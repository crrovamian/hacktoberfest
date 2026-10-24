---
target: demo swipe Pisomatch (index.html)
total_score: 17
max_score: 40
na_heuristics: 
p0_count: 0
p1_count: 3
timestamp: 2026-10-24T13-51-13Z
slug: pruebas-src-main-resources-static-index-html
---
# Critique — Demo de swipe (PisoMatch)

Target: `pruebas/src/main/resources/static/index.html` (182 KB de HTML único, 342 líneas)
Modo: Operate (el usuario completa una tarea: decidir sobre candidatos y chatear)

## Design Health Score

| # | Heurística | Nota | Problema clave |
|---|---|---|---|
| 1 | Visibilidad del estado | 2 | Sin spinner ni estado de carga; el swipe no tiene animación; errores de red solo en `console.error` |
| 2 | Sistema ↔ mundo real | 3 | Español natural, pero "100% compatible" no se explica y "Eres:" es un selector de identidad poco claro |
| 3 | Control y libertad | 1 | Un *pass* es irreversible; sin undo; overlays sin Esc; no se puede volver a un candidato descartado |
| 4 | Consistencia y estándares | 2 | Botones circulares vs. filas "toca para chatear" que son `div`; emoji mezclados con iconos |
| 5 | Prevención de errores | 1 | "Pasar" no confirma ni se puede deshacer; acción destructiva a un toque |
| 6 | Reconocer > recordar | 2 | Botones solo-icono (`✕`, `♥`) sin etiqueta visible; no hay leyenda; el % no se desglosa |
| 7 | Flexibilidad y eficiencia | 1 | Sin teclado (←/→), sin gesto de arrastre, sin atajos; solo dos botones |
| 8 | Diseño estético y minimalista | 3 | Tarjeta limpia y enfocada; pero la jerarquía pone la foto por encima de los datos de decisión |
| 9 | Recuperación de errores | 1 | La tarjeta se elimina del mazo antes del POST; si falla, el swipe se pierde en silencio |
| 10 | Ayuda y documentación | 1 | Nada explica cómo se calcula la compatibilidad ni qué significan los tags |
| **Total** | | **17/40** | **Poor (43%)** |

## Veredicto de especificidad

**Intercambiable.** Quitando el nombre "PisoMatch" y el presupuesto, esta pantalla es un clon genérico de Tinder: foto a sangre, badge de porcentaje, dos círculos verde/rojo y una lista de matches. Los datos que de verdad deciden un piso compartido —presupuesto, zona, fecha de entrada, hábitos, mascotas— están reducidos a *pills* grises de 12px debajo de una bio genérica. El propio dominio ya tiene `Vivienda`, pero el mazo no muestra ninguna vivienda.

## Lo que funciona

- **Una decisión por pantalla**: una sola tarjeta protagonista, sin menús ni filtros compitiendo. Carga cognitiva baja.
- **Botones grandes**: 68×68 px, por encima del mínimo táctil de 44 px, bien separados.
- **Continuidad del flujo**: el match desemboca en una lista de matches con chat ya precargado, y la demo arranca con datos reales.

## Problemas prioritarios

### [P1] Un "pass" no se puede deshacer
**Por qué**: el gesto destructivo está a un toque y no hay vuelta atrás. En una app de swipe el error más común es el toque accidental; sin undo, el usuario pierde un candidato para siempre.
**Fix**: añadir botón "Deshacer" (↺) que reaparezca la última tarjeta. Requiere backend: `DELETE /api/v1/usuarios/{id}/swipes/{objetivoId}` o un estado `UNDO` en `LikeRepository` (hoy la restricción única impide re-swipear).

### [P1] El swipe se pierde si falla la red
**Por qué**: en `swipe()` (líneas 258-277) se hace `queue.shift()` y se re-renderiza **antes** del `POST`; el `catch` solo hace `console.error`. El usuario ve desaparecer la tarjeta y cree que guardó su like. Además no hay `aria-live` ni toast.
**Fix**: optimistic UI con rollback, toast de error ("No hemos podido guardar tu like. Reintentar") y `try/catch` que devuelva la tarjeta a la cabeza del mazo.

### [P1] El mazo no se puede deslizar y no responde al teclado
**Por qué**: el producto se vende como "swipe", pero solo hay dos botones. No hay `pointerdown/move/up`, ni rotación/inclinación, ni vuelo de la tarjeta, ni gesto de teclado (←/→). En móvil, el pulgar espera arrastrar la tarjeta.
**Fix**: implementar drag con `pointer events` + `transform: translate/rotate` + umbral (~80px) + animación de salida; soportar `ArrowLeft/ArrowRight`, `Escape` para cerrar overlays, y `prefers-reduced-motion`.

### [P2] La información que decide no es la que se ve
**Por qué**: presupuesto, ciudad y fecha de entrada son pills diminutos (`tagsFor`, línea 179); el peso visual se lo lleva la bio. Y el "100% compatible" (línea 210) no se puede auditar: un badge verde sin desglose no genera confianza.
**Fix**: subir presupuesto + zona + fecha a la zona superior de la tarjeta como datos primarios; añadir un desplegable "¿Por qué 100%?" con el desglose de `CompatibilityCalculator` (zona 40 / presupuesto 40 / ciudad 20). Aprovechar `Vivienda` para mostrar el piso asociado.

### [P2] El match es un callejón sin salida
**Por qué**: el overlay (líneas 124-129) solo ofrece "Seguir buscando". El pico emocional del producto no lleva a la acción natural (chatear), no tiene `role="dialog"`, ni foco, ni Esc, ni `aria-live`, y el `img` está vacío. El rojo al 95% de opacidad tampoco se celebra: satura.
**Fix**: CTA principal "Enviar mensaje" + secundario "Seguir buscando"; foco al botón primario, trampa de foco, Esc, `aria-live="assertive"`, animación breve de entrada y confeti discreto.

### [P2] Accesibilidad estructural
**Por qué**: botones `✕`/`♥` sin `aria-label` (solo `title`); filas de match con `onclick` en `div` (no enfocables); sin `:focus-visible`; contraste blanco sobre `#ff5a5f` = **3.05:1** (falla AA para texto normal); blanco sobre verde `#22c55e` = **2.28:1**; overlays sin gestionar foco ni bloquear el scroll del body.
**Fix**: `aria-label`/`<button>` reales, `tabindex`/`<a>` en filas, anillo de foco visible, oscurecer la marca o usar texto oscuro sobre verde, `inert`/`overflow:hidden` al abrir overlay, `role="dialog" aria-modal="true"`.

## Detector (Assessment B)

3 warnings, 0 errores:
- `broken-image` en líneas 127 y 134: `overlay-img` y `chat-img` sin `src` hasta que se abren. **Falso positivo parcial**: se rellenan por JS; sí falta alto/ancho reservado (salto de layout) y `alt` significativo.
- `single-font` en línea 14: una sola familia tipográfica para todo. Cierto, pero impacto bajo en un demo.
- Evidencia adicional: **0** media queries, **0** `aria-*`, **0** listeners de teclado, **0** `pointer/touch/drag`, **0** `focus-visible`, altura fija `520px`, imágenes remotas (pravatar/picsum) sin `loading="lazy"` ni `width/height`.

Navegador: no disponible en esta sesión (sin Playwright/Chromium), así que no hubo overlay visual.

## Personas

**Casey (móvil, una mano)**: el mazo mide 520px fijos y la lista de matches queda bajo el pliegue; los CTAs están en el centro, no en la zona del pulgar; no puede deslizar; el select "Eres:" del header es incómodo de alcanzar.

**Jordan (primerizo)**: "100% compatible" sin explicación; `✕`/`♥` solo-icono; no sabe si el like se guardó; al llegar a "No hay más usuarios" no hay CTA para cambiar de usuario (solo el select del header).

**Sam (teclado/lector)**: no puede recorrer el mazo ni dar like sin ratón; las filas de match no reciben foco; el overlay de match no se anuncia; contraste 3.05:1 en header y chat.

## Observaciones menores

- `innerHTML` con datos de la API sin escapar en la tarjeta (líneas 208-217) y en matches (246-252): riesgo de XSS; el chat sí escapa.
- `alert()` para errores de chat (línea 321) rompe el tono y bloquea.
- No hay `disabled` visual claro durante la petición, ni bloqueo de doble envío en el chat.
- El chat no se refresca: mensajes entrantes no aparecen hasta reabrir.
- `url('')` en `background-image` cuando no hay foto deja un bloque gris sin inicial/placeholder.
- Falta `<meta name="theme-color">` y favicon; el header no es sticky.
- No hay `aria-current` ni indicador de "quedan N candidatos".

## Preguntas

- ¿Y si la tarjeta mostrara el piso (precio, zona, fotos) en vez de una bio genérica? Ese es el producto.
- ¿El "% compatible" debe ser el héroe visual, o debería ser el precio?
- ¿Cuánto del gesto de Tinder es requisito y cuánto es costumbre copiada?
- ¿Qué pasa cuando se acaban los candidatos: callejón o siguiente paso claro?
