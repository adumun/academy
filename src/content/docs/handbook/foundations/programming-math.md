---
title: "Matemáticas para programar"
description: "Contadores, acumuladores, promedios, porcentajes y límites."
---

**Tipo:** explicación y guía práctica. **Nivel:** esencial a complementario.

## Traducir una operación

Un contador registra ocurrencias; un acumulador reúne valores. Para promediar mediciones 3, 5 y 7, acumula 15 y divide por 3: resultado 5. Si no hay mediciones, el promedio no está definido sin una política adicional. El tipo elegido determina precisión y rango.

| Operación | Ejemplo |
|---|---|
| Resto | 10 dividido por 4 deja resto 2 |
| Paridad | Un entero es par si su resto al dividir por 2 es cero |
| Porcentaje | 20% de 150 = 150 × 20 / 100 = 30 |
| Variación | Aumentar 150 un 20% da 180 |
| Promedio | Suma de valores dividida por cantidad no nula |

## Comprobación

Calcula 20% de 150 y compara con 150 × 0,2. Revisa cero, números negativos, cantidades grandes y el comportamiento esperado en límites. Un formato de pantalla no cambia necesariamente la precisión del valor. Para dinero se requiere una política de representación exacta y redondeo.

## Magnitud, unidades y precisión

Una cantidad debe interpretarse junto con su unidad. No sumes metros y centímetros sin convertir; no confundas porcentaje con cantidad absoluta. Una tasa del 20 % se representa como 0,2 al multiplicar. Si el valor base es cero, un porcentaje de variación relativa no está definido sin una convención adicional.

### Contar y acumular no son lo mismo

```text
contador := 0
suma := 0
para cada valor en [3, 5, 7]:
    contador := contador + 1
    suma := suma + valor
si contador > 0:
    promedio := suma / contador
si no:
    informar ausencia de promedio
```

| Paso | Contador | Suma |
|---|---|---|
| Inicial | 0 | 0 |
| 3 | 1 | 3 |
| 5 | 2 | 8 |
| 7 | 3 | 15 |

El promedio es 5. Para una secuencia vacía no lo sustituyas por cero, porque cero podría ser un promedio válido. Una división entera y una decimal pueden tener resultados distintos según el lenguaje. Un valor aproximado en punto flotante no es equivalente a una representación decimal exacta.

## Ejemplos de contraste

Aumentar 100 en 20 % produce 120; disminuir 120 en 20 % produce 96, no 100, porque cambia la base. Para comprobar paridad interesa el resto cero, incluso con enteros negativos; para envolver posiciones o ciclos hay que definir cómo se trata un resto negativo. Documenta límites y política de redondeo antes de implementar.

## Conceptos relacionados

- [Valores y tipos](/handbook/foundations/values-and-types/)
- [Algoritmos](/handbook/foundations/programs/)

## Aplicaciones

- [Tipos y conversiones en Java](/handbook/java/types/)
- [Operadores en Java](/handbook/java/expressions/)

## Procedencia didáctica

Adaptación original de [material docente](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/docs/extras/matematicas-para-programar/README.md), versión fija; los ejemplos son independientes.
