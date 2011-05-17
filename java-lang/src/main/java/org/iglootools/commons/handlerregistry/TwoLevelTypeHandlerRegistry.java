package org.iglootools.commons.handlerregistry;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

/**
 * @author Sami Dalouche (sami.dalouche@gmail.com)
 */
public class TwoLevelTypeHandlerRegistry<H> {
    public static class TwoLevelTypeHandlerRegistryBuilder<H> {
        private static class Trio<H> {
            private Class<?> firstLevelClass;
            private Class<?> secondLevelClass;
            private H handler;

            Trio(Class<?> firstLevelClass, Class<?> secondLevelClass, H handler) {
                this.firstLevelClass = firstLevelClass;
                this.secondLevelClass = secondLevelClass;
                this.handler = handler;
            }

            public Class<?> getFirstLevelClass() {
                return firstLevelClass;
            }

            public Class<?> getSecondLevelClass() {
                return secondLevelClass;
            }

            public H getHandler() {
                return handler;
            }
        }
        private List<Trio<H>> trios = Lists.newArrayList();

        public TwoLevelTypeHandlerRegistryBuilder<H> addSpecificationHandler(Class<?> firstLevelClass, Class<?> secondLevelClass,H handler) {
            trios.add(new Trio<H>(firstLevelClass, secondLevelClass, handler));
            return this;
        }

        public TwoLevelTypeHandlerRegistryBuilder<H> addSpecificationHandlerForSeveralClasses(Iterable<? extends Class<?>> firstLevelClasses, Iterable<? extends Class<?>> secondLevelClasses, H handler) {
            for(Class<?> firstLevelClass : firstLevelClasses) {
                for(Class<?> secondLevelClass : secondLevelClasses) {
                    addSpecificationHandler(firstLevelClass, secondLevelClass, handler);
                }
            }
            return this;
        }

        public TwoLevelTypeHandlerRegistry<H> build() {
            HandlerRegistries.HandlerRegistryBuilder<Class<?>, HandlerRegistry<Class<?>, H>> topLevelRegistryBuilder =
                    new HandlerRegistries.HandlerRegistryBuilder<Class<?>,HandlerRegistry<Class<?>, H>>();

            for(Class<?> c : allFirstLevelClasses(trios)) {
                Set<Trio<H>> associatedTrios = associatedTrios(trios, c);
                topLevelRegistryBuilder.addHandler(c, createSecondLevelRegistry(associatedTrios));
            }
            return new TwoLevelTypeHandlerRegistry(topLevelRegistryBuilder.buildTypeRegistry());
        }

        private HandlerRegistry<Class<?>, H> createSecondLevelRegistry(Set<Trio<H>> associatedTrios) {
            HandlerRegistries.HandlerRegistryBuilder<Class<?>, H> secondLevelRegistryBuilder = new HandlerRegistries.HandlerRegistryBuilder<Class<?>, H>();
            for(Trio<H> t: associatedTrios) {
                secondLevelRegistryBuilder.addHandler(t.getSecondLevelClass(), t.handler);
            }
            return secondLevelRegistryBuilder.buildTypeRegistry();
        }

        private Set<Trio<H>> associatedTrios(final List<? extends Trio<H>> trios, final Class<?> firstLevelClass) {
            return Sets.newHashSet(Iterables.filter(trios, new Predicate<Trio>(){
                @Override
                public boolean apply(Trio input) {
                    return input.firstLevelClass.equals(firstLevelClass);
                }
            }));
        }

        private Set<Class<?>> allFirstLevelClasses(List<? extends Trio> trios) {
            return Sets.newHashSet(Iterables.transform(trios, new Function<Trio, Class<?>>() {
                @Override
                public Class<?> apply(Trio t) {
                    return t.getFirstLevelClass();
                }
            }));
        }
    }

    public static<H> TwoLevelTypeHandlerRegistryBuilder<H> twoLevelRegistryBuilder(Class<H> handlerType) {
        return new TwoLevelTypeHandlerRegistryBuilder<H>();
    }

    private HandlerRegistry<Class<?>, HandlerRegistry<Class<?>, H>> registry;

    private TwoLevelTypeHandlerRegistry(HandlerRegistry<Class<?>, HandlerRegistry<Class<?>, H>> registry) {
        this.registry = registry;
    }

    public H findHandlerFor(Class<?> firstLevelClass, Class<?> secondLevelClass) {
        HandlerRegistry<Class<?>, H> secondLevelLocator = registry.findHandlerFor(firstLevelClass);
        return secondLevelLocator.findHandlerFor(secondLevelClass);
    }
}

