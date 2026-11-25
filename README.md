# ADÜMÜN Academy

Biblioteca pública de conocimiento técnico y rutas de aprendizaje. Astro + Starlight, salida estática.

```bash
npm install
npm run dev
npm run build
```

La base del Handbook está publicada mediante Cloudflare Pages. El incremento de referencias conectadas se trabaja en un PR separado.

```bash
npm run test:taxonomy
npm run validate:content
npm run validate:taxonomy
npm run test:java-examples
```

Los tres primeros controles son parte del build. La suite Java requiere JDK 21 y comprueba ejemplos Java autónomos; no necesita MariaDB ni interfaz gráfica. El lockfile aún debe generarse y verificarse en un entorno con acceso al registro npm.

- [Despliegue](docs/DEPLOYMENT.md)
- [Lineamientos para contribuciones](AGENTS.md)
- [Política editorial y taxonomía](docs/EDITORIAL.md)
- [ADU-93](https://adumun.atlassian.net/browse/ADU-93)
- [Academic Labs](https://academic-labs.adumun.cl/)
