package org.iglootools.commons.handlerregistry;

import com.google.common.collect.ImmutableSet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.is;

public class TwoLevelTypeHandlerRegistryTest {

    private static interface AnyHandlerInterface {}
    private static class AnyClass {}
    private static class MyClass {}
    private static class AnotherClass {};
    private static class SubclassOfMyClass extends MyClass {}


    private TwoLevelTypeHandlerRegistry<AnyHandlerInterface> handlerLocator;

    @Before
    public void setup(){
        handlerLocator = TwoLevelTypeHandlerRegistry
                .twoLevelRegistryBuilder(AnyHandlerInterface.class)
                .build();
    }

    @Test(expected = NoMatchingHandlerFoundException.class)
    public void shouldNotFindAnyHandler() {
        handlerLocator.findHandlerFor(AnyClass.class, MyClass.class);
    }

    @Test(expected = NoMatchingHandlerFoundException.class)
    public void shouldNotFindAnyHandlerBecauseFirstLevelClassDoesNotMatch() {
        AnyHandlerInterface handler = Mockito.mock(AnyHandlerInterface.class);
        handlerLocator = TwoLevelTypeHandlerRegistry
                .twoLevelRegistryBuilder(AnyHandlerInterface.class)
                .addSpecificationHandler(AnotherClass.class, MyClass.class, handler)
                .build();
        handlerLocator.findHandlerFor(AnyClass.class, MyClass.class);
    }

    @Test(expected = NoMatchingHandlerFoundException.class)
    public void shouldNotFindAnyHandlerBecauseSecondLevelClassDoesNotMatch() {
        AnyHandlerInterface handler = Mockito.mock(AnyHandlerInterface.class);
        handlerLocator = TwoLevelTypeHandlerRegistry
                .twoLevelRegistryBuilder(AnyHandlerInterface.class)
                .addSpecificationHandler(AnyClass.class, AnotherClass.class, handler)
                .build();

        handlerLocator.findHandlerFor(AnyClass.class, MyClass.class);
    }

    @Test
    public void shouldFindHandler() {
        AnyHandlerInterface handler = Mockito.mock(AnyHandlerInterface.class);
        handlerLocator = TwoLevelTypeHandlerRegistry
                .twoLevelRegistryBuilder(AnyHandlerInterface.class)
                .addSpecificationHandler(AnyClass.class, AnotherClass.class, handler)
                .build();

        Assert.assertThat(handlerLocator.findHandlerFor(AnyClass.class, AnotherClass.class), is(handler));
    }

    @Test
    public void shouldFindHandlerAddedUsingSeveralClasses() {
        AnyHandlerInterface handler = Mockito.mock(AnyHandlerInterface.class);
               handlerLocator = TwoLevelTypeHandlerRegistry
                       .twoLevelRegistryBuilder(AnyHandlerInterface.class)
                       .addSpecificationHandler(AnyClass.class, AnotherClass.class, handler)
                       .addSpecificationHandlerForSeveralClasses(ImmutableSet.of(AnyClass.class, MyClass.class), ImmutableSet.of(AnotherClass.class), handler)
                       .build();

        Assert.assertThat(handlerLocator.findHandlerFor(AnyClass.class, AnotherClass.class), is(handler));
        Assert.assertThat(handlerLocator.findHandlerFor(MyClass.class, AnotherClass.class), is(handler));
    }

    @Test
    public void shouldFindHandlerUsingSuperclassForFirstLevel() {
        AnyHandlerInterface handler = Mockito.mock(AnyHandlerInterface.class);
        handlerLocator = TwoLevelTypeHandlerRegistry
                .twoLevelRegistryBuilder(AnyHandlerInterface.class)
                .addSpecificationHandler(MyClass.class, AnotherClass.class, handler)
                .build();

        Assert.assertThat(handlerLocator.findHandlerFor(SubclassOfMyClass.class, AnotherClass.class), is(handler));
    }

    @Test
    public void shouldFindHandlerUsingSuperclassForSecondLevel() {
        AnyHandlerInterface handler = Mockito.mock(AnyHandlerInterface.class);
        handlerLocator = TwoLevelTypeHandlerRegistry
                .twoLevelRegistryBuilder(AnyHandlerInterface.class)
                .addSpecificationHandler(AnotherClass.class, MyClass.class, handler)
                .build();

        Assert.assertThat(handlerLocator.findHandlerFor(AnotherClass.class, SubclassOfMyClass.class), is(handler));
    }

}
