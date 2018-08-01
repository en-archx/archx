package co.en.archx.archx;

public interface INavigator<S extends INavigator.Signal> {

    interface Signal {}

    void navigate(S signal);
}
