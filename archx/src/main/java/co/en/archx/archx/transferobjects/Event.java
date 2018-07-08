package co.en.archx.archx.transferobjects;

public interface Event<A extends Action> {

    A toAction();
}
