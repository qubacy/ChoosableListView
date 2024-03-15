# ChoosableListView library
## Decisen by swiping

[![Android](./assets/badges/os_android.svg)](https://developer.android.com/develop) [![Kotlin](./assets/badges/lang_kotlin.svg)](https://kotlinlang.org/docs/home.html) [![JitPack](https://jitpack.io/v/qubacy/ChoosableListView.svg)](https://jitpack.io/#qubacy/ChoosableListView)

## Table of Contents

- [Description](#description)
- [System Requirements](#system-requirements)
- [Installation](#installation)
- [Instructions](#instructions)
- [Testing](#testing)
- [Dependencies](#dependencies)
- [Contributors](#contributors)

## Description
Feel free to use this tiny library to implement a RecyclerView-based list with choosable items. By choosable, I mean it can be swiped aside (Right or Left) to trigger a specific action. As the main feature, in the process of swiping, the user will be provided with dynamically inflated hints (an icon with a caption under it).

![Preview GIF](./assets/gifs/preview.gif)

## System Requirements

Your device has to meet the following requirements:
- Android OS with API Level $\geq$ 21 (version 5.0 or higher).

## Installation

In order to use the library, one needs to do the following steps:

**Step 1.** Add it in your root build.gradle at the end of repositories:
```java
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
      mavenCentral()
      maven { url 'https://jitpack.io' }
    }
}
```
**Step 2.** Add the dependency
```java
dependencies {
  implementation 'com.github.qubacy:ChoosableListView:*LIB_VERSION_CODE*'
}
```

## Instructions

Once you got the lib, it'd be nice to customize it's elements' appearence. It can be done by defining the following attributes' values in your app's *theme*.

Attribute | Meaning | Value type
--- | --- | ---
`choosableListItemLeftSwipeBackgroundColor` | Background color of the list item content view when it's swiped left | *Color*
`choosableListItemRightSwipeBackgroundColor` | Background color of the list item content view when it's swiped right | *Color*
`choosableListItemLeftSwipeHintViewIcon` | An icon for the left background Hint | *AnimatedVectorDrawableCompat*
`choosableListItemLeftSwipeHintViewIconTint` | An icon's tint for the left background Hint | *Color*
`choosableListItemLeftSwipeHintViewText` | A caption for the left background Hint | *String*
`choosableListItemLeftSwipeHintViewTextColor` | A caption's color for the left background Hint | *Color*
`choosableListItemRightSwipeHintViewIcon` | An icon for the right background Hint | *AnimatedVectorDrawableCompat*
`choosableListItemRightSwipeHintViewIconTint` | An icon's tint for the right background Hint | *Color*
`choosableListItemRightSwipeHintViewText` | A caption for the right background Hint | *String*
`choosableListItemRightSwipeHintViewTextColor` | A caption's color for the right background Hint | *Color*
`choosableListItemHintIconAnimationDuration` | A duration of the Hint's icon animation | *Integer*
`choosableListItemHeight` | Height of the list item | *Dimension*
`choosableListItemHintThreshold` | A threshold that is used for setting a swiping offset when the Hint is shown | *Fraction*
`choosableListItemLeftHintGuidelinePosition` | Defines a position for both Hints' vertical guidelines. They are used as the second anchor for placing the Hints in the middle between them and the Item View's closest border | *Fraction*
`choosableListItemHintIconSize` | The Hint's icon size | *Dimension*

## Testing

Almost all crucial components of the lib have been covered with tests (Unit & Instrumental).

## Dependencies

The following noticeable libraries & frameworks are in use in the app:
- [Mockito](https://github.com/mockito/mockito) (for mocking classes' objects in tests);
- [Espresso](https://developer.android.com/training/testing/espresso) (for Instrumental Testing);

## Contributors

The library was fully made by Semyon Dzukaev in 2024. All rights reserved.
