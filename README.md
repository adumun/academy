# ADÜMÜN Academy

Biblioteca pública de conocimiento técnico y rutas de aprendizaje. Astro + Starlight, salida estática.

```bash
npm install
npm run dev
npm run build
```

La base del Handbook está publicada mediante Cloudflare Pages. El incremento de referencias conectadas se trabaja en un PR separado.

```bash
npm run validate:content
npm run test:java-examples
```

La primera orden es parte del build. La segunda requiere JDK 21 y comprueba ejemplos Java autónomos; no necesita MariaDB ni interfaz gráfica. El lockfile aún debe generarse y verificarse en un entorno con acceso al registro npm.

- [Despliegue](docs/DEPLOYMENT.md)
- [Política editorial](docs/EDITORIAL.md)
- [ADU-93](https://adumun.atlassian.net/browse/ADU-93)
- [Academic Labs](https://academic-labs.adumun.cl/)
