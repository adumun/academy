import { readdirSync, readFileSync, existsSync } from 'node:fs';
import { join, relative, sep } from 'node:path';
import { fileURLToPath } from 'node:url';

const publicRoot = fileURLToPath(new URL('../public/', import.meta.url));
const root = fileURLToPath(new URL('../src/content/docs/', import.meta.url));

function markdownFiles(directory) {
    return readdirSync(directory, { withFileTypes: true })
        .flatMap(entry => {
            const path = join(directory, entry.name);
            if (entry.isDirectory()) {
                return markdownFiles(path);
            }
            return entry.isFile() && entry.name.endsWith('.md') ? [path] : [];
        })
        .sort();
}

function routeFor(path) {
    return '/' + relative(root, path)
        .split(sep).join('/')
        .replace(/(^|\/)index\.md$/, '$1')
        .replace(/\.md$/, '/');
}

const files = markdownFiles(root);
const routes = new Set();
const errors = [];
let links = 0;
let codeBlocks = 0;
let diagrams = 0;

for (const path of files) {
    const route = routeFor(path);
    if (routes.has(route)) {
        errors.push('Duplicate route: ' + route);
    }
    routes.add(route);
}

for (const path of files) {
    const name = relative(root, path);
    const md = readFileSync(path, 'utf8').replace(/\r\n/g, '\n');
    const header = md.match(/^---\n([\s\S]*?)\n---\n/);

    if (!header || !/^title:\s*\S/m.test(header[1])
            || !/^description:\s*\S/m.test(header[1])) {
        errors.push(name + ': missing title or description frontmatter');
    }

    const body = header ? md.slice(header[0].length) : md;
    let fence = null;

    for (const [index, line] of body.split('\n').entries()) {
        const marker = line.match(/^ {0,3}(\x60{3,}|~{3,})(.*)$/);

        if (marker) {
            if (!fence) {
                fence = marker[1];
                codeBlocks++;
                continue;
            }
            if (marker[1][0] === fence[0]
                    && marker[1].length >= fence.length
                    && !marker[2].trim()) {
                fence = null;
                continue;
            }
        }

        if (fence) {
            continue;
        }

        // The current editorial convention uses root-relative Markdown links.
        // This intentionally validates route existence, not anchor or external HTTP health.
        for (const match of line.matchAll(/\]\((\/[^)\s]+)\)/g)) {
            const target = match[1].split(/[?#]/)[0];
            const opening = line.lastIndexOf('[', match.index);
            if (opening > 0 && line[opening - 1] === '!') {
                if (!/^\/diagrams\/[a-z0-9-]+\.svg$/.test(target)
                        || !line.slice(opening + 1, match.index).trim()
                        || !existsSync(join(publicRoot, target.slice(1)))) {
                    errors.push(name + ': ' + (index + 1) + ': invalid diagram asset or alt text ' + target);
                }
                diagrams++;
                continue;
            }
            links++;
            if (!routes.has(target)) {
                errors.push(name + ': ' + (index + 1) + ': missing route ' + target);
            }
        }
    }

    if (fence) {
        errors.push(name + ': unclosed code fence');
    }
}

if (files.length === 0) {
    errors.push('No documentation pages found');
}

console.log('Content: ' + files.length + ' pages, ' + links
    + ' internal links, ' + codeBlocks + ' code blocks, ' + diagrams + ' diagram references.');

if (errors.length > 0) {
    console.error(errors.join('\n'));
    process.exitCode = 1;
} else {
    console.log('PASS: route existence, frontmatter, fenced code blocks, local diagram assets and alt text.');
}
