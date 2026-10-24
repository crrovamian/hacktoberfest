package com.pisomatch.domain;

/**
 * Estado de un {@link Like} dirigido de un usuario a otro.
 * <ul>
 *   <li>{@code LIKE}: me interesa, a la espera de que el otro corresponda.</li>
 *   <li>{@code PASS}: descartado.</li>
 *   <li>{@code MATCH}: like recíproco; ambos se han gustado.</li>
 * </ul>
 */
public enum EstadoLike {
    LIKE,
    PASS,
    MATCH
}
