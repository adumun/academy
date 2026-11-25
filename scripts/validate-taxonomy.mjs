import { readdirSync, readFileSync } from 'node:fs';
import { join, relative, sep } from 'node:path';
import { fileURLToPath } from 'node:url';
import { classifyRoute, inspectKnowledgePage } from './knowledge-taxonomy.mjs';

const root = fileURLToPath(new URL('../src/content/docs/', import.meta.url));

function files(directory) {
    return readdirSync(directory, { withFileTypes: true }).flatMap(entry => {
        const path = join(directory, entry.name);
        if (entry.isDirectory()) {
            return files(path);
        }
        return entry.isFile() && entry.name.endsWith('.md') ? [path] : [];
    }).sort();
}

const errors = [];
const counts = {};

for (const path of files(root)) {
    const route = '/' + relative(root, path).split(sep).join('/')
        .replace(/(^|\/)index\.md$/, '$1').replace(/\.md$/, '/');
    const scope = classifyRoute(route);
    counts[scope ?? 'unclassified'] = (counts[scope ?? 'unclassified'] ?? 0) + 1;

    for (const issue of inspectKnowledgePage(route, readFileSync(path, 'utf8'))) {
        errors.push(route + ':' + issue.line + ': ' + issue.message);
    }
}

console.log('Knowledge scopes: ' + JSON.stringify(counts));
if (errors.length) {
    console.error(errors.join('\n'));
    process.exitCode = 1;
} else {
    console.log('PASS: editorial scope classification and prerequisite direction.');
}
