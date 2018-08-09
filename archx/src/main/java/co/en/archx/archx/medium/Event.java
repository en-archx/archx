package co.en.archx.archx.medium;

/**
 * <p>
 *     Events, usually a UI events.
 *     <br><br>
 *     Example:<br>
 *     A click event or bottom of list event.
 * </p>
 *
 * <pre> {@code
 * LoadDogsClicked
 * } </pre>
 */
public interface Event<A extends Action> {

    A toAction();
}
