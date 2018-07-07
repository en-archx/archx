package co.en.archx.archx

interface INavigator<S: INavigator.Signal> {

    interface Signal

    fun navigate(signal: S)
}