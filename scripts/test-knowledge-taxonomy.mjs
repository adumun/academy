import test from 'node:test';
import assert from 'node:assert/strict';
import { classifyRoute, inspectKnowledgePage } from './knowledge-taxonomy.mjs';

const foundation = '/handbook/foundations/objects/';
const java = '/handbook/java/classes-and-objects/';
const javafx = '/handbook/java/javafx/table-view/';
const jdbc = '/handbook/java/jdbc/connection/';

function page(heading, target) {
    return '## ' + heading + '\n\n- [Concepto](' + target + ')\n';
}

test('general foundation cannot require Java', () => {
    assert.equal(inspectKnowledgePage(foundation, page('Conocimientos previos recomendados', java)).length, 1);
});
test('Java cannot require a framework', () => {
    assert.equal(inspectKnowledgePage(java, page('Conocimientos previos recomendados', javafx)).length, 1);
});
test('JavaFX can require Java and a general foundation', () => {
    assert.deepEqual(inspectKnowledgePage(javafx, page('Conocimientos previos recomendados', java)
        + '- [General](' + foundation + ')\n'), []);
});
test('JavaFX cannot require JDBC for general control reference', () => {
    assert.equal(inspectKnowledgePage(javafx, page('Conocimientos previos recomendados', jdbc)).length, 1);
});
test('general definition cannot rely on a concrete framework link', () => {
    assert.equal(inspectKnowledgePage(foundation, page('Definición', javafx)).length, 1);
});
test('general page can link implementations and related topics', () => {
    assert.deepEqual(inspectKnowledgePage(foundation, page('Aplicaciones e implementaciones', java)
        + page('Conceptos relacionados', javafx)), []);
});
test('a code block is not an editorial link', () => {
    assert.deepEqual(inspectKnowledgePage(foundation, '## Definición\n~~~text\n[Demo](' + javafx + ')\n~~~\n'), []);
});
test('navigation and applied legacy routes are explicit', () => {
    assert.equal(classifyRoute('/paths/dsy1102/'), 'navigation');
    assert.equal(classifyRoute('/handbook/java/fundamentals/'), 'application');
    assert.equal(classifyRoute('/handbook/architecture/'), 'application');
});
test('unknown route and self prerequisite fail', () => {
    assert.equal(inspectKnowledgePage('/handbook/unknown/', '').length, 1);
    assert.equal(inspectKnowledgePage(java, page('Conocimientos previos recomendados', java)).length, 1);
});
test('architecture pattern does not require Java', () => {
    const observer = '/handbook/architecture/observer/';
    assert.equal(inspectKnowledgePage(observer, page('Conocimientos previos recomendados', java)).length, 1);
    assert.deepEqual(inspectKnowledgePage(observer, page('Aplicación: ejemplo Java', java)), []);
});

test('project consumes knowledge, never the reverse as a prerequisite', () => {
    const project = '/projects/petcare/';
    assert.equal(classifyRoute(project), 'project');
    assert.deepEqual(inspectKnowledgePage(project, page('Conocimientos previos recomendados', java)), []);
    assert.equal(inspectKnowledgePage(java, page('Conocimientos previos recomendados', project)).length, 1);
    assert.deepEqual(inspectKnowledgePage(java, page('Aplicaciones', project)), []);
});
