---
title: "Memoria, almacenamiento y recuperación"
description: "Estado en memoria, persistencia, fallos, respaldos y recuperación."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario.

## Tres representaciones distintas

Un programa mantiene [valores y objetos](/handbook/foundations/objects/) en memoria durante su ejecución. El almacenamiento persistente conserva una representación más allá del proceso. Una interfaz puede mostrar una copia o vista de esos datos. Ninguna se sincroniza automáticamente con las demás.

| Estado | Comprobación |
|---|---|
| En memoria | Inspeccionar datos del proceso |
| Persistido | Leer el archivo o consultar el almacenamiento de forma independiente |
| Recuperado | Reiniciar el proceso y verificar lo reconstruido |

Un archivo ausente puede significar primera ejecución; un archivo inválido es un fallo, no una colección vacía. Un respaldo contiene otra versión de los datos; no garantiza recuperación automática. La política de recuperación debe decidir qué versión se restaura, con autorización cuando corresponda y sin destruir evidencia del fallo.

## Escrituras seguras

Una estrategia típica serializa a un temporal en la misma ubicación, valida, conserva la versión anterior y reemplaza el destino según garantías del sistema. Un reemplazo atómico evita ciertos estados intermedios visibles, pero no garantiza durabilidad frente a cualquier fallo. Concurrencia y acceso multiusuario requieren coordinación adicional.

## Comprobación

Con datos desechables, guarda dos valores, cierra el proceso y recupera ambos. Después modifica una copia del archivo de modo inválido y observa si se informa el error sin sobrescribirlo. No simules fallos destruyendo datos importantes.

## Qué ocurre realmente al guardar y recuperar

![Una vista muestra el estado en memoria; guardar escribe una representación en almacenamiento; recuperar lee el almacenamiento y reconstruye el estado. La vista no garantiza que se guardó.](/diagrams/persistence-state.svg)

La persistencia es una propiedad del almacenamiento y del procedimiento que lo utiliza: permite recuperar información después de finalizar el proceso. No significa guardar automáticamente cada cambio de una variable. Guardar y recuperar son operaciones explícitas con contratos propios. Serializar convierte valores a una representación; almacenar escribe esa representación en un medio; recuperar la lee y reconstruye el estado.

### Traza de estados

| Acción | Memoria | Archivo |
|---|---|---|
| Iniciar con archivo válido | A | A |
| Agregar B sin guardar | A, B | A |
| Guardar correctamente | A, B | A, B |
| Editar externamente a C | A, B | C |
| Reiniciar y leer correctamente | C | C |

Esta traza supone que no hay sincronización automática. Si se encuentra corrupción, no es correcto reconstruir una colección vacía y anunciar éxito. Conserva evidencia y aplica la política explícita de recuperación. Un respaldo puede ser anterior al estado actual y necesita validación antes de restaurar.

## Diseñar un contrato de guardado

Especifica cuándo se considera exitoso, qué ocurre si falla escritura o validación, quién coordina accesos concurrentes y qué se observa al reiniciar. No asumas que reemplazar un archivo de forma atómica es equivalente a garantizar durabilidad o coordinar varios procesos.

## Conceptos relacionados

- [JSON](/handbook/foundations/json/)
- [Operaciones y contratos](/handbook/foundations/operations/)

## Aplicaciones

- [Modelo relacional](/handbook/sql/relational-model/)
- [Path y Files en Java](/handbook/java/files/)
- [Transacciones JDBC](/handbook/java/jdbc/transactions/)

## Procedencia didáctica

Adaptación original de [material docente versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/docs/extras/puentes-unidad-02/04-memoria-archivo-y-recuperacion.md). Los ejemplos no requieren un proyecto de negocio específico.
