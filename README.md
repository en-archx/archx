# archx

`Archx` is an experimental architecture developed to provide consistency in every application component structure, making it predictable or easy to read. 

The architecture is build using `rxjava`. Its reactive nature will serve as an extra layer of abstraction from/to each MVP component. View push events to Presenter using a stream, Presenter will push states to View also using a stream. There is a good reason for doing this, and mainly to manage state. Jake 

Like any kind of `mvp`, `Archx` have `model-view-presenter`(obviously) but with a buffed presenter.

#### The `Presenter`
`Archx`'s `presenter` extends [Googles AAC](https://github.com/googlesamples/android-architecture-components/tree/master/GithubBrowserSample) `ViewModel` for two reasons;

1. It out-lives android-components (`Activity`, `Fragment`) on configuration changes.
2. It can be shared between multiple android-components.

Using `Googles AAC` `ViewModel` will also mean that the consumer of this library will have to implement their own ViewModelFactory.


**Anatomy**, the `presenter` contains two `Relay`s, one for incoming `events` and one for outgoing `states`. States in this architecture is yield using previous state and `action-results`, since previous states is needed, `rx` `scan` operator is used in its main stream to hold the latest `state`. [WIP]a

#### The `View`
[WIP]

#### The `Model`
[WIP]

### To install the library

#### Gradle
```gradle
implementation 'co.en.archx:archx:0.0.1'
```

#### Maven
```xml
<dependency>
  <groupId>co.en.archx</groupId>
  <artifactId>archx</artifactId>
  <version>0.0.1</version>
  <type>pom</type>
</dependency>
```
