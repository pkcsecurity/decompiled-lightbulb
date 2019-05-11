package io.reactivex.parallel;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.annotations.BackpressureKind;
import io.reactivex.annotations.BackpressureSupport;
import io.reactivex.annotations.Beta;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.Experimental;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.LongConsumer;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.parallel.ParallelCollect;
import io.reactivex.internal.operators.parallel.ParallelConcatMap;
import io.reactivex.internal.operators.parallel.ParallelDoOnNextTry;
import io.reactivex.internal.operators.parallel.ParallelFilter;
import io.reactivex.internal.operators.parallel.ParallelFilterTry;
import io.reactivex.internal.operators.parallel.ParallelFlatMap;
import io.reactivex.internal.operators.parallel.ParallelFromArray;
import io.reactivex.internal.operators.parallel.ParallelFromPublisher;
import io.reactivex.internal.operators.parallel.ParallelJoin;
import io.reactivex.internal.operators.parallel.ParallelMap;
import io.reactivex.internal.operators.parallel.ParallelMapTry;
import io.reactivex.internal.operators.parallel.ParallelPeek;
import io.reactivex.internal.operators.parallel.ParallelReduce;
import io.reactivex.internal.operators.parallel.ParallelReduceFull;
import io.reactivex.internal.operators.parallel.ParallelRunOn;
import io.reactivex.internal.operators.parallel.ParallelSortedJoin;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.ListAddBiConsumer;
import io.reactivex.internal.util.MergerBiFunction;
import io.reactivex.internal.util.SorterFunction;
import io.reactivex.parallel.ParallelFailureHandling;
import io.reactivex.parallel.ParallelFlowableConverter;
import io.reactivex.parallel.ParallelTransformer;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Beta
public abstract class ParallelFlowable<T extends Object> {

   @CheckReturnValue
   public static <T extends Object> ParallelFlowable<T> from(@NonNull Publisher<? extends T> var0) {
      return from(var0, Runtime.getRuntime().availableProcessors(), Flowable.bufferSize());
   }

   @CheckReturnValue
   public static <T extends Object> ParallelFlowable<T> from(@NonNull Publisher<? extends T> var0, int var1) {
      return from(var0, var1, Flowable.bufferSize());
   }

   @CheckReturnValue
   @NonNull
   public static <T extends Object> ParallelFlowable<T> from(@NonNull Publisher<? extends T> var0, int var1, int var2) {
      ObjectHelper.requireNonNull(var0, "source");
      ObjectHelper.verifyPositive(var1, "parallelism");
      ObjectHelper.verifyPositive(var2, "prefetch");
      return RxJavaPlugins.onAssembly(new ParallelFromPublisher(var0, var1, var2));
   }

   @CheckReturnValue
   @NonNull
   public static <T extends Object> ParallelFlowable<T> fromArray(@NonNull Publisher<T> ... var0) {
      if(var0.length == 0) {
         throw new IllegalArgumentException("Zero publishers not supported");
      } else {
         return RxJavaPlugins.onAssembly(new ParallelFromArray(var0));
      }
   }

   @CheckReturnValue
   @Experimental
   @NonNull
   public final <R extends Object> R as(@NonNull ParallelFlowableConverter<T, R> var1) {
      return ((ParallelFlowableConverter)ObjectHelper.requireNonNull(var1, "converter is null")).apply(this);
   }

   @CheckReturnValue
   @NonNull
   public final <C extends Object> ParallelFlowable<C> collect(@NonNull Callable<? extends C> var1, @NonNull BiConsumer<? super C, ? super T> var2) {
      ObjectHelper.requireNonNull(var1, "collectionSupplier is null");
      ObjectHelper.requireNonNull(var2, "collector is null");
      return RxJavaPlugins.onAssembly(new ParallelCollect(this, var1, var2));
   }

   @CheckReturnValue
   @NonNull
   public final <U extends Object> ParallelFlowable<U> compose(@NonNull ParallelTransformer<T, U> var1) {
      return RxJavaPlugins.onAssembly(((ParallelTransformer)ObjectHelper.requireNonNull(var1, "composer is null")).apply(this));
   }

   @CheckReturnValue
   @NonNull
   public final <R extends Object> ParallelFlowable<R> concatMap(@NonNull Function<? super T, ? extends Publisher<? extends R>> var1) {
      return this.concatMap(var1, 2);
   }

   @CheckReturnValue
   @NonNull
   public final <R extends Object> ParallelFlowable<R> concatMap(@NonNull Function<? super T, ? extends Publisher<? extends R>> var1, int var2) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.verifyPositive(var2, "prefetch");
      return RxJavaPlugins.onAssembly(new ParallelConcatMap(this, var1, var2, ErrorMode.IMMEDIATE));
   }

   @CheckReturnValue
   @NonNull
   public final <R extends Object> ParallelFlowable<R> concatMapDelayError(@NonNull Function<? super T, ? extends Publisher<? extends R>> var1, int var2, boolean var3) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.verifyPositive(var2, "prefetch");
      ErrorMode var4;
      if(var3) {
         var4 = ErrorMode.END;
      } else {
         var4 = ErrorMode.BOUNDARY;
      }

      return RxJavaPlugins.onAssembly(new ParallelConcatMap(this, var1, var2, var4));
   }

   @CheckReturnValue
   @NonNull
   public final <R extends Object> ParallelFlowable<R> concatMapDelayError(@NonNull Function<? super T, ? extends Publisher<? extends R>> var1, boolean var2) {
      return this.concatMapDelayError(var1, 2, var2);
   }

   @CheckReturnValue
   @NonNull
   public final ParallelFlowable<T> doAfterNext(@NonNull Consumer<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "onAfterNext is null");
      return RxJavaPlugins.onAssembly(new ParallelPeek(this, Functions.emptyConsumer(), var1, Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.emptyConsumer(), Functions.EMPTY_LONG_CONSUMER, Functions.EMPTY_ACTION));
   }

   @CheckReturnValue
   @NonNull
   public final ParallelFlowable<T> doAfterTerminated(@NonNull Action var1) {
      ObjectHelper.requireNonNull(var1, "onAfterTerminate is null");
      return RxJavaPlugins.onAssembly(new ParallelPeek(this, Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, var1, Functions.emptyConsumer(), Functions.EMPTY_LONG_CONSUMER, Functions.EMPTY_ACTION));
   }

   @CheckReturnValue
   @NonNull
   public final ParallelFlowable<T> doOnCancel(@NonNull Action var1) {
      ObjectHelper.requireNonNull(var1, "onCancel is null");
      return RxJavaPlugins.onAssembly(new ParallelPeek(this, Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.emptyConsumer(), Functions.EMPTY_LONG_CONSUMER, var1));
   }

   @CheckReturnValue
   @NonNull
   public final ParallelFlowable<T> doOnComplete(@NonNull Action var1) {
      ObjectHelper.requireNonNull(var1, "onComplete is null");
      return RxJavaPlugins.onAssembly(new ParallelPeek(this, Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.emptyConsumer(), var1, Functions.EMPTY_ACTION, Functions.emptyConsumer(), Functions.EMPTY_LONG_CONSUMER, Functions.EMPTY_ACTION));
   }

   @CheckReturnValue
   @NonNull
   public final ParallelFlowable<T> doOnError(@NonNull Consumer<Throwable> var1) {
      ObjectHelper.requireNonNull(var1, "onError is null");
      return RxJavaPlugins.onAssembly(new ParallelPeek(this, Functions.emptyConsumer(), Functions.emptyConsumer(), var1, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.emptyConsumer(), Functions.EMPTY_LONG_CONSUMER, Functions.EMPTY_ACTION));
   }

   @CheckReturnValue
   @NonNull
   public final ParallelFlowable<T> doOnNext(@NonNull Consumer<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "onNext is null");
      return RxJavaPlugins.onAssembly(new ParallelPeek(this, var1, Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.emptyConsumer(), Functions.EMPTY_LONG_CONSUMER, Functions.EMPTY_ACTION));
   }

   @CheckReturnValue
   @Experimental
   @NonNull
   public final ParallelFlowable<T> doOnNext(@NonNull Consumer<? super T> var1, @NonNull BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> var2) {
      ObjectHelper.requireNonNull(var1, "onNext is null");
      ObjectHelper.requireNonNull(var2, "errorHandler is null");
      return RxJavaPlugins.onAssembly(new ParallelDoOnNextTry(this, var1, var2));
   }

   @CheckReturnValue
   @Experimental
   @NonNull
   public final ParallelFlowable<T> doOnNext(@NonNull Consumer<? super T> var1, @NonNull ParallelFailureHandling var2) {
      ObjectHelper.requireNonNull(var1, "onNext is null");
      ObjectHelper.requireNonNull(var2, "errorHandler is null");
      return RxJavaPlugins.onAssembly(new ParallelDoOnNextTry(this, var1, var2));
   }

   @CheckReturnValue
   @NonNull
   public final ParallelFlowable<T> doOnRequest(@NonNull LongConsumer var1) {
      ObjectHelper.requireNonNull(var1, "onRequest is null");
      return RxJavaPlugins.onAssembly(new ParallelPeek(this, Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.emptyConsumer(), var1, Functions.EMPTY_ACTION));
   }

   @CheckReturnValue
   @NonNull
   public final ParallelFlowable<T> doOnSubscribe(@NonNull Consumer<? super Subscription> var1) {
      ObjectHelper.requireNonNull(var1, "onSubscribe is null");
      return RxJavaPlugins.onAssembly(new ParallelPeek(this, Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, var1, Functions.EMPTY_LONG_CONSUMER, Functions.EMPTY_ACTION));
   }

   @CheckReturnValue
   public final ParallelFlowable<T> filter(@NonNull Predicate<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "predicate");
      return RxJavaPlugins.onAssembly(new ParallelFilter(this, var1));
   }

   @CheckReturnValue
   @Experimental
   public final ParallelFlowable<T> filter(@NonNull Predicate<? super T> var1, @NonNull BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> var2) {
      ObjectHelper.requireNonNull(var1, "predicate");
      ObjectHelper.requireNonNull(var2, "errorHandler is null");
      return RxJavaPlugins.onAssembly(new ParallelFilterTry(this, var1, var2));
   }

   @CheckReturnValue
   @Experimental
   public final ParallelFlowable<T> filter(@NonNull Predicate<? super T> var1, @NonNull ParallelFailureHandling var2) {
      ObjectHelper.requireNonNull(var1, "predicate");
      ObjectHelper.requireNonNull(var2, "errorHandler is null");
      return RxJavaPlugins.onAssembly(new ParallelFilterTry(this, var1, var2));
   }

   @CheckReturnValue
   @NonNull
   public final <R extends Object> ParallelFlowable<R> flatMap(@NonNull Function<? super T, ? extends Publisher<? extends R>> var1) {
      return this.flatMap(var1, false, Integer.MAX_VALUE, Flowable.bufferSize());
   }

   @CheckReturnValue
   @NonNull
   public final <R extends Object> ParallelFlowable<R> flatMap(@NonNull Function<? super T, ? extends Publisher<? extends R>> var1, boolean var2) {
      return this.flatMap(var1, var2, Integer.MAX_VALUE, Flowable.bufferSize());
   }

   @CheckReturnValue
   @NonNull
   public final <R extends Object> ParallelFlowable<R> flatMap(@NonNull Function<? super T, ? extends Publisher<? extends R>> var1, boolean var2, int var3) {
      return this.flatMap(var1, var2, var3, Flowable.bufferSize());
   }

   @CheckReturnValue
   @NonNull
   public final <R extends Object> ParallelFlowable<R> flatMap(@NonNull Function<? super T, ? extends Publisher<? extends R>> var1, boolean var2, int var3, int var4) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.verifyPositive(var3, "maxConcurrency");
      ObjectHelper.verifyPositive(var4, "prefetch");
      return RxJavaPlugins.onAssembly(new ParallelFlatMap(this, var1, var2, var3, var4));
   }

   @CheckReturnValue
   @NonNull
   public final <R extends Object> ParallelFlowable<R> map(@NonNull Function<? super T, ? extends R> var1) {
      ObjectHelper.requireNonNull(var1, "mapper");
      return RxJavaPlugins.onAssembly(new ParallelMap(this, var1));
   }

   @CheckReturnValue
   @Experimental
   @NonNull
   public final <R extends Object> ParallelFlowable<R> map(@NonNull Function<? super T, ? extends R> var1, @NonNull BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> var2) {
      ObjectHelper.requireNonNull(var1, "mapper");
      ObjectHelper.requireNonNull(var2, "errorHandler is null");
      return RxJavaPlugins.onAssembly(new ParallelMapTry(this, var1, var2));
   }

   @CheckReturnValue
   @Experimental
   @NonNull
   public final <R extends Object> ParallelFlowable<R> map(@NonNull Function<? super T, ? extends R> var1, @NonNull ParallelFailureHandling var2) {
      ObjectHelper.requireNonNull(var1, "mapper");
      ObjectHelper.requireNonNull(var2, "errorHandler is null");
      return RxJavaPlugins.onAssembly(new ParallelMapTry(this, var1, var2));
   }

   public abstract int parallelism();

   @CheckReturnValue
   @NonNull
   public final Flowable<T> reduce(@NonNull BiFunction<T, T, T> var1) {
      ObjectHelper.requireNonNull(var1, "reducer");
      return RxJavaPlugins.onAssembly(new ParallelReduceFull(this, var1));
   }

   @CheckReturnValue
   @NonNull
   public final <R extends Object> ParallelFlowable<R> reduce(@NonNull Callable<R> var1, @NonNull BiFunction<R, ? super T, R> var2) {
      ObjectHelper.requireNonNull(var1, "initialSupplier");
      ObjectHelper.requireNonNull(var2, "reducer");
      return RxJavaPlugins.onAssembly(new ParallelReduce(this, var1, var2));
   }

   @CheckReturnValue
   @NonNull
   public final ParallelFlowable<T> runOn(@NonNull Scheduler var1) {
      return this.runOn(var1, Flowable.bufferSize());
   }

   @CheckReturnValue
   @NonNull
   public final ParallelFlowable<T> runOn(@NonNull Scheduler var1, int var2) {
      ObjectHelper.requireNonNull(var1, "scheduler");
      ObjectHelper.verifyPositive(var2, "prefetch");
      return RxJavaPlugins.onAssembly(new ParallelRunOn(this, var1, var2));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> sequential() {
      return this.sequential(Flowable.bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   @NonNull
   public final Flowable<T> sequential(int var1) {
      ObjectHelper.verifyPositive(var1, "prefetch");
      return RxJavaPlugins.onAssembly(new ParallelJoin(this, var1, false));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   @Experimental
   @NonNull
   public final Flowable<T> sequentialDelayError() {
      return this.sequentialDelayError(Flowable.bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   @NonNull
   public final Flowable<T> sequentialDelayError(int var1) {
      ObjectHelper.verifyPositive(var1, "prefetch");
      return RxJavaPlugins.onAssembly(new ParallelJoin(this, var1, true));
   }

   @CheckReturnValue
   @NonNull
   public final Flowable<T> sorted(@NonNull Comparator<? super T> var1) {
      return this.sorted(var1, 16);
   }

   @CheckReturnValue
   @NonNull
   public final Flowable<T> sorted(@NonNull Comparator<? super T> var1, int var2) {
      ObjectHelper.requireNonNull(var1, "comparator is null");
      ObjectHelper.verifyPositive(var2, "capacityHint");
      return RxJavaPlugins.onAssembly(new ParallelSortedJoin(this.reduce(Functions.createArrayList(var2 / this.parallelism() + 1), ListAddBiConsumer.instance()).map(new SorterFunction(var1)), var1));
   }

   public abstract void subscribe(@NonNull Subscriber<? super T>[] var1);

   @CheckReturnValue
   @NonNull
   public final <U extends Object> U to(@NonNull Function<? super ParallelFlowable<T>, U> var1) {
      try {
         Object var3 = ((Function)ObjectHelper.requireNonNull(var1, "converter is null")).apply(this);
         return var3;
      } catch (Throwable var2) {
         Exceptions.throwIfFatal(var2);
         throw ExceptionHelper.wrapOrThrow(var2);
      }
   }

   @CheckReturnValue
   @NonNull
   public final Flowable<List<T>> toSortedList(@NonNull Comparator<? super T> var1) {
      return this.toSortedList(var1, 16);
   }

   @CheckReturnValue
   @NonNull
   public final Flowable<List<T>> toSortedList(@NonNull Comparator<? super T> var1, int var2) {
      ObjectHelper.requireNonNull(var1, "comparator is null");
      ObjectHelper.verifyPositive(var2, "capacityHint");
      return RxJavaPlugins.onAssembly(this.reduce(Functions.createArrayList(var2 / this.parallelism() + 1), ListAddBiConsumer.instance()).map(new SorterFunction(var1)).reduce(new MergerBiFunction(var1)));
   }

   protected final boolean validate(@NonNull Subscriber<?>[] var1) {
      int var2 = this.parallelism();
      if(var1.length == var2) {
         return true;
      } else {
         StringBuilder var4 = new StringBuilder();
         var4.append("parallelism = ");
         var4.append(var2);
         var4.append(", subscribers = ");
         var4.append(var1.length);
         IllegalArgumentException var5 = new IllegalArgumentException(var4.toString());
         int var3 = var1.length;

         for(var2 = 0; var2 < var3; ++var2) {
            EmptySubscription.error(var5, var1[var2]);
         }

         return false;
      }
   }
}
