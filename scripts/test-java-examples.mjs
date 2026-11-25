import { mkdtempSync, readFileSync, writeFileSync, rmSync } from 'node:fs';
import { tmpdir } from 'node:os';
import { join } from 'node:path';
import { spawnSync } from 'node:child_process';
import { fileURLToPath } from 'node:url';

const root = fileURLToPath(new URL('../src/content/docs/', import.meta.url));
const cases = [
    ["handbook/java/constructors.md","ConstructorDemo","10\n5\nRechazado"],
    ["handbook/java/composition.md","ComposicionDemo","Centro: 0,0"],
    ["handbook/java/inheritance.md","HerenciaDemo","Normal\nCritico"],
    ["handbook/java/abstract-classes.md","AbstraccionDemo","21 C"],
    ["handbook/java/arrays.md","ArraysDemo","3\n20\n60"],
    ["handbook/java/sets.md","SetDemo","true\nfalse\ntrue\n2"],
    ["handbook/java/maps.md","MapDemo","10\n15\nfalse\n2"],
    ["handbook/java/equality.md","IgualdadDemo","false\ntrue\n1"],
    ["handbook/java/scanner.md","ScannerDemo","21\nSol"],
    ['handbook/java/classes-and-objects.md', 'Libro', 'Java\nSQL'],
    ['handbook/java/generics.md', 'Caja', 'Java\n21'],
    ['handbook/java/collections.md', 'ColeccionesDemo', 'Java\nSQL\nJava\nTotal: 3'],
    ['handbook/java/interfaces.md', 'CatalogoDemo', '[Java, SQL]'],
    ['handbook/java/exceptions.md', 'RecursosDemo', 'Java'],
    ['handbook/java/lambdas.md', 'LambdasDemo', 'Java\nSQL'],
    ['handbook/java/optional.md', 'OptionalDemo', 'Java\nSin título'],
    ['handbook/java/dates.md', 'FechasDemo', '2026-11-25\n2026-11-26\ntrue'],
    ['handbook/architecture/observer.md', 'ObservableDemo', 'Cambio'],
];

function run(command, args, cwd) {
    const result = spawnSync(command, args, {
        cwd,
        encoding: 'utf8',
        timeout: 20000,
    });

    if (result.error || result.status !== 0) {
        throw new Error(command + ': ' + (result.error || result.stderr));
    }

    return result.stdout.replace(/\r\n/g, '\n').trimEnd();
}

const work = mkdtempSync(join(tmpdir(), 'academy-java-'));
let passed = 0;

try {
    for (const [page, className, expected] of cases) {
        const md = readFileSync(join(root, page), 'utf8').replace(/\r\n/g, '\n');
        const blocks = [...md.matchAll(/^\x60{3}java\n([\s\S]*?)\n^\x60{3}\s*$/gm)]
            .map(match => match[1])
            .filter(source => new RegExp('\\bpublic class ' + className + '\\b').test(source));

        if (blocks.length !== 1) {
            throw new Error(page + ': expected exactly one example for ' + className);
        }

        const filename = className + '.java';
        writeFileSync(join(work, filename), blocks[0] + '\n', 'utf8');
        run('javac', ['--release', '21', '-encoding', 'UTF-8', filename], work);
        const actual = run('java', ['-Dstdout.encoding=UTF-8', '-Dstderr.encoding=UTF-8', '-cp', work, className], work);

        if (actual !== expected) {
            throw new Error(className + ': unexpected output ' + JSON.stringify(actual));
        }

        passed++;
        console.log('PASS ' + className);
    }

    console.log('Java examples: ' + passed + '/' + cases.length + ' PASS');
} finally {
    rmSync(work, { recursive: true, force: true });
}
