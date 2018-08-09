package co.en.archx.archx.medium;

/**
 * <p>
 *     Results, or better called as action results.
 *     <br><br>
 *     Example:<br>
 *     A result derived from performing the action.
 * </p>
 *
 * <pre> {@code
 *
 * LoadDogs -> DogsLoading
 * or
 * LoadDogs -> DogsLoadFail
 * or
 * LoadDogs -> DogsLoaded
 * } </pre>
 */
public interface Result { }
