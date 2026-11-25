// Editorial scopes only. This is not a competency or learning graph.
export function classifyRoute(route) {
    if (route === '/' || route === '/handbook/' || route.startsWith('/paths/')) {
        return 'navigation';
    }

    // Stable legacy URLs whose contents intentionally combine concerns.
    if (route === '/handbook/java/fundamentals/'
            || route === '/handbook/architecture/'
            || route === '/handbook/troubleshooting/') {
        return 'application';
    }

    const prefixes = [
        ['/projects/', 'project'],
        ['/handbook/foundations/', 'foundation'],
        ['/handbook/java/jdbc/', 'jdbc'],
        ['/handbook/java/javafx/', 'javafx'],
        ['/handbook/java/', 'java'],
        ['/handbook/sql/', 'data'],
        ['/handbook/architecture/', 'architecture'],
        ['/handbook/tools/maven/', 'maven'],
    ];

    return prefixes.find(([prefix]) => route.startsWith(prefix))?.[1] ?? null;
}

const allowedPrerequisites = {
    foundation: ['foundation'],
    java: ['foundation', 'java'],
    data: ['foundation', 'data'],
    architecture: ['foundation', 'architecture'],
    maven: ['foundation', 'java', 'maven'],
    jdbc: ['foundation', 'java', 'data', 'architecture', 'maven', 'jdbc'],
    javafx: ['foundation', 'java', 'architecture', 'maven', 'javafx'],
    application: ['foundation', 'java', 'data', 'architecture', 'maven',
        'jdbc', 'javafx', 'application'],
    project: ['foundation', 'java', 'data', 'architecture', 'maven',
        'jdbc', 'javafx', 'application'],
    navigation: ['foundation', 'java', 'data', 'architecture', 'maven',
        'jdbc', 'javafx', 'application', 'navigation', 'project'],
};

export function checkKnowledgeLink(source, target, section) {
    const from = classifyRoute(source);
    const to = classifyRoute(target);

    if (!from || !to) {
        return 'unclassified knowledge route';
    }

    if (section === 'prerequisite') {
        if (source === target) {
            return 'self prerequisite';
        }
        if (!allowedPrerequisites[from].includes(to)) {
            return 'invalid prerequisite direction: ' + from + ' -> ' + to;
        }
    }

    // Canonical general definitions must not rely on specific implementations.
    // Reverse navigation is welcome when explicitly identified as such.
    if (from === 'foundation' && to !== 'foundation'
            && section !== 'application' && section !== 'related') {
        return 'foundation references a specific scope outside application/related section';
    }

    return null;
}

function sectionKind(heading) {
    const name = heading.normalize('NFD').replace(/\p{Diacritic}/gu, '').toLowerCase();
    if (/^(conocimientos previos|prerrequisitos|requisitos previos)/.test(name)) {
        return 'prerequisite';
    }
    if (/^(aplicacion|aplicaciones|implementacion|implementaciones|ejemplo|ejemplos|practicar)/.test(name)) {
        return 'application';
    }
    if (/^(conceptos relacionados|referencias relacionadas|siguientes conocimientos)/.test(name)) {
        return 'related';
    }
    return 'definition';
}

export function inspectKnowledgePage(source, markdown) {
    const issues = [];
    if (!classifyRoute(source)) {
        issues.push({ line: 1, message: 'unclassified page: ' + source });
        return issues;
    }

    let section = 'definition';
    let fence = null;
    let frontmatter = false;
    const lines = markdown.replace(/\r\n/g, '\n').split('\n');

    for (const [index, line] of lines.entries()) {
        if (index === 0 && line === '---') {
            frontmatter = true;
            continue;
        }
        if (frontmatter) {
            if (line === '---') {
                frontmatter = false;
            }
            continue;
        }

        const marker = line.match(/^ {0,3}(\x60{3,}|~{3,})(.*)$/);
        if (marker) {
            if (!fence) {
                fence = marker[1];
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

        const heading = line.match(/^#{1,6}\s+(.+)$/);
        if (heading) {
            section = sectionKind(heading[1]);
        }

        for (const match of line.matchAll(/\]\((\/[^)\s]+)\)/g)) {
            const target = match[1].split(/[?#]/)[0];
            const message = checkKnowledgeLink(source, target, section);
            if (message) {
                issues.push({ line: index + 1, message: message + ': ' + target });
            }
        }
    }

    return issues;
}
