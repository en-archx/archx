package co.en.archx.archx;


import co.en.archx.archx.medium.Action;
import co.en.archx.archx.medium.Event;
import co.en.archx.archx.medium.State;
import io.reactivex.Observable;

public interface IView<E extends Event<? extends Action>, S extends State> {

    Observable<E> events();

    void render(S state);

}
