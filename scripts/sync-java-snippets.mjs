import { readFileSync, writeFileSync } from 'node:fs';
import { join } from 'node:path';
import { fileURLToPath } from 'node:url';

const root = fileURLToPath(new URL('../', import.meta.url));
const write = process.argv.includes('--write');
const manifest = JSON.parse(readFileSync(join(root, 'examples/java/handbook/snippets.json'), 'utf8'));
const mavenManifest = JSON.parse(readFileSync(join(root, 'examples/java/handbook/maven-snippets.json'), 'utf8'));
const pages = new Set();
let count = 0;
for (const entry of [...manifest, ...mavenManifest]) {
    if (pages.has(entry.page)) throw new Error('Duplicate snippet page: ' + entry.page);
    pages.add(entry.page);
    const path = join(root, 'src/content/docs', entry.page);
    const original = readFileSync(path, 'utf8').replace(/\r\n/g, '\n');
    let md = original;
    const ids = new Set();
    const languages = new Set(entry.snippets.map(snippet => snippet.language || 'java'));
    for (const snippet of entry.snippets) {
        if (ids.has(snippet.id)) throw new Error('Duplicate snippet: ' + snippet.id);
        ids.add(snippet.id);
        const language = snippet.language || 'java';
        if (!['java', 'xml'].includes(language) || (language === 'xml' && snippet.region) || !/^[a-zA-Z0-9/-]+\.(?:java|fxml|xml)$/.test(snippet.file) || (language === 'java' ? !snippet.file.endsWith('.java') : !/\.(?:fxml|xml)$/.test(snippet.file)) || !/^[a-z0-9-]+$/.test(snippet.id)) {
            throw new Error('Invalid snippet path/id');
        }
        let source = readFileSync(join(root, 'examples/java/handbook', snippet.file), 'utf8').replace(/\r\n/g, '\n').trimEnd();
        if (snippet.region) {
            const lines = source.split('\n');
            const start = lines.findIndex(l => l.trim() === '// snippet:' + snippet.region + ':start');
            const end = lines.findIndex(l => l.trim() === '// snippet:' + snippet.region + ':end');
            if (start < 0 || end <= start + 1) throw new Error('Invalid region: ' + snippet.id);
            const region = lines.slice(start + 1, end);
            const indent = Math.min(...region.filter(l => l.trim()).map(l => l.match(/^ */)[0].length));
            source = region.map(l => l.slice(indent)).join('\n');
        }
        const marker = '<!-- example:' + snippet.id + ' -->';
        const pattern = new RegExp(marker + '\\n```' + language + '\\n([\\s\\S]*?)\\n```(?=\\n|$)', 'g');
        const matches = [...md.matchAll(pattern)];
        if (matches.length !== 1) throw new Error('Expected one marker: ' + snippet.id);
        if (!write && matches[0][1] !== source) throw new Error('Snippet drift: ' + snippet.id);
        md = md.replace(pattern, () => marker + '\n```' + language + '\n' + source + '\n```');
        count++;
    }
    const markers = [...md.matchAll(/<!-- example:([a-z0-9-]+) -->/g)].map(m => m[1]);
    if (markers.length !== ids.size || markers.some(id => !ids.has(id))) throw new Error('Unregistered marker: ' + entry.page);
    const sourceBlocks = [...md.matchAll(/^```(java|xml)\n[\s\S]*?\n^```\s*$/gm)].filter(match => languages.has(match[1]));
    if (sourceBlocks.length !== ids.size) throw new Error('Unregistered source block: ' + entry.page);
    if (write && md !== original) writeFileSync(path, md, 'utf8');
}
console.log('Canonical snippets: ' + count + ' ' + (write ? 'synchronized' : 'PASS'));
