import { test } from 'node:test';
import assert from 'node:assert/strict';
import { compilerErrorCodes, matchesCompilerRejection } from './java-compiler-diagnostics.mjs';

const staticContext = 'StaticContext.java:4:9: compiler.err.non-static.cant.be.ref: kindname.method, consultar()\n1 error\n';
const noArguments = 'InvalidOverride.java:2:5: compiler.err.method.does.not.override.superclass\n1 error\n';
const nested = 'InterfaceAccess.java:2:12: compiler.err.override.weaker.access: (compiler.misc.cant.implement: describir(), A, B), public\n1 error\n';

test('preserves the hyphenated diagnostic from StaticContext CI failure', () => {
    assert.deepEqual(compilerErrorCodes(staticContext), ['compiler.err.non-static.cant.be.ref']);
    assert.equal(matchesCompilerRejection({ status: 1, stderr: staticContext }, 'compiler.err.non-static.cant.be.ref'), true);
});
test('accepts a diagnostic with no arguments and CRLF', () => {
    assert.deepEqual(compilerErrorCodes(noArguments.replaceAll('\n', '\r\n')), ['compiler.err.method.does.not.override.superclass']);
});
test('extracts primary code, not nested diagnostic arguments', () => {
    assert.deepEqual(compilerErrorCodes(nested), ['compiler.err.override.weaker.access']);
    assert.deepEqual(compilerErrorCodes('A.java:1:1: compiler.err.prob.found.req: compiler.err.other'), ['compiler.err.prob.found.req']);
});
test('rejects wrong codes, including a matching prefix', () => {
    assert.equal(matchesCompilerRejection({ status: 1, stderr: staticContext }, 'compiler.err.non'), false);
    assert.equal(matchesCompilerRejection({ status: 1, stderr: staticContext }, 'compiler.err.cant.resolve.location'), false);
});
test('rejects multiple primary errors', () => {
    assert.equal(matchesCompilerRejection({ status: 1, stderr: staticContext + noArguments }, 'compiler.err.non-static.cant.be.ref'), false);
});
test('rejects successful compilation, launch failure, and missing diagnostics', () => {
    assert.equal(matchesCompilerRejection({ status: 0, stderr: staticContext }, 'compiler.err.non-static.cant.be.ref'), false);
    assert.equal(matchesCompilerRejection({ status: 1, stderr: staticContext, error: new Error('timeout') }, 'compiler.err.non-static.cant.be.ref'), false);
    assert.equal(matchesCompilerRejection({ status: 1, stderr: 'unexpected failure' }, 'compiler.err.non-static.cant.be.ref'), false);
});
