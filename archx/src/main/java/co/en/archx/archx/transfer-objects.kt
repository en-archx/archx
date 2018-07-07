package co.en.archx.archx

interface Event<A: Action> {

    fun toAction(): A
}

interface Action

interface Result

interface State