#!/usr/bin/env bash
set -euo pipefail
image=mariadb:11.4
container="academy-jdbc-${GITHUB_RUN_ID:-local}-$$"
root_password="$(openssl rand -hex 24)"
export DB_USER=academy_lab
export DB_PASSWORD="$(openssl rand -hex 24)"
if [[ -n "${GITHUB_ACTIONS:-}" ]]; then
    echo "::add-mask::$root_password"
    echo "::add-mask::$DB_PASSWORD"
fi
docker pull "$image"
docker image inspect --format '{{index .RepoDigests 0}}' "$image"
docker run --rm -d --name "$container" \
    -p 127.0.0.1:3306:3306 \
    -e MARIADB_ROOT_PASSWORD="$root_password" "$image" >/dev/null
trap 'docker rm -f "$container" >/dev/null 2>&1 || true' EXIT
ready=false
for attempt in $(seq 1 60); do
    if docker exec "$container" healthcheck.sh --connect --innodb_initialized >/dev/null 2>&1; then
        ready=true
        break
    fi
    sleep 2
done
if [[ "$ready" != true ]]; then
    echo "MariaDB did not become ready"
    exit 1
fi
docker exec -i "$container" sh -c 'MYSQL_PWD="$MARIADB_ROOT_PASSWORD" exec mariadb -uroot' <<SQL
CREATE DATABASE preparacion_db;
CREATE USER 'academy_lab'@'%' IDENTIFIED BY '$DB_PASSWORD';
GRANT SELECT, INSERT, UPDATE, DELETE ON preparacion_db.* TO 'academy_lab'@'%';
USE preparacion_db;
CREATE TABLE libro (id INT AUTO_INCREMENT PRIMARY KEY, titulo VARCHAR(120) NOT NULL) ENGINE=InnoDB;
CREATE TABLE detalle_libro (id INT AUTO_INCREMENT PRIMARY KEY, cantidad INT NULL, fecha_apertura DATE NULL, precio DECIMAL(10,2) NULL) ENGINE=InnoDB;
SELECT VERSION();
SQL
mvn -version
mvn -B -ntp -f examples/java/handbook/maven/jdbc/pom.xml clean test
python3 - <<'PY'
import xml.etree.ElementTree as ET
from pathlib import Path
report = Path('examples/java/handbook/maven/jdbc/target/surefire-reports/TEST-JdbcConnectionTest.xml')
root = ET.parse(report).getroot()
expected = {'conexionValidaYEsquema', 'salidaDocumentada', 'credencialesInvalidas', 'permisosMinimos', 'lecturaTablaYRecursos'}
cases = root.findall('testcase')
assert len(cases) == len(expected) and {c.attrib['name'] for c in cases} == expected
assert all(c.find(tag) is None for c in cases for tag in ('failure', 'error', 'skipped'))
print('MariaDB connection: 5/5 PASS')
report = report.with_name('TEST-JdbcStatementsTest.xml')
root = ET.parse(report).getroot()
expected = {'buscarPresenciaAusencia', 'actualizarConteo', 'tituloComoDato',
            'claveGenerada', 'fechaNulaYValor', 'lecturaFragmento',
            'cursorCeroUnaVarias', 'mapeoSeparado', 'nullYConversiones',
            'parametrosInvalidos', 'etiquetaInexistente', 'cursorEnMemoria'}
cases = root.findall('testcase')
assert len(cases) == len(expected) and {c.attrib['name'] for c in cases} == expected
assert all(c.find(tag) is None for c in cases for tag in ('failure', 'error', 'skipped'))
print('MariaDB statements and result set: 12/12 PASS')
PY
