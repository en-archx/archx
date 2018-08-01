package co.en.archx.archx.medium;

public interface Event<A extends Action> {

    A toAction();
}
