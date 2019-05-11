package io.reactivex;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.SingleConverter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleOperator;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.annotations.BackpressureKind;
import io.reactivex.annotations.BackpressureSupport;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.Experimental;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Function4;
import io.reactivex.functions.Function5;
import io.reactivex.functions.Function6;
import io.reactivex.functions.Function7;
import io.reactivex.functions.Function8;
import io.reactivex.functions.Function9;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.fuseable.FuseToMaybe;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.internal.observers.BiConsumerSingleObserver;
import io.reactivex.internal.observers.BlockingMultiObserver;
import io.reactivex.internal.observers.ConsumerSingleObserver;
import io.reactivex.internal.observers.FutureSingleObserver;
import io.reactivex.internal.operators.completable.CompletableFromSingle;
import io.reactivex.internal.operators.completable.CompletableToFlowable;
import io.reactivex.internal.operators.flowable.FlowableConcatMap;
import io.reactivex.internal.operators.flowable.FlowableConcatMapPublisher;
import io.reactivex.internal.operators.flowable.FlowableFlatMapPublisher;
import io.reactivex.internal.operators.flowable.FlowableSingleSingle;
import io.reactivex.internal.operators.maybe.MaybeFilterSingle;
import io.reactivex.internal.operators.maybe.MaybeFromSingle;
import io.reactivex.internal.operators.observable.ObservableConcatMap;
import io.reactivex.internal.operators.observable.ObservableSingleSingle;
import io.reactivex.internal.operators.single.SingleAmb;
import io.reactivex.internal.operators.single.SingleCache;
import io.reactivex.internal.operators.single.SingleContains;
import io.reactivex.internal.operators.single.SingleCreate;
import io.reactivex.internal.operators.single.SingleDefer;
import io.reactivex.internal.operators.single.SingleDelay;
import io.reactivex.internal.operators.single.SingleDelayWithCompletable;
import io.reactivex.internal.operators.single.SingleDelayWithObservable;
import io.reactivex.internal.operators.single.SingleDelayWithPublisher;
import io.reactivex.internal.operators.single.SingleDelayWithSingle;
import io.reactivex.internal.operators.single.SingleDetach;
import io.reactivex.internal.operators.single.SingleDoAfterSuccess;
import io.reactivex.internal.operators.single.SingleDoAfterTerminate;
import io.reactivex.internal.operators.single.SingleDoFinally;
import io.reactivex.internal.operators.single.SingleDoOnDispose;
import io.reactivex.internal.operators.single.SingleDoOnError;
import io.reactivex.internal.operators.single.SingleDoOnEvent;
import io.reactivex.internal.operators.single.SingleDoOnSubscribe;
import io.reactivex.internal.operators.single.SingleDoOnSuccess;
import io.reactivex.internal.operators.single.SingleEquals;
import io.reactivex.internal.operators.single.SingleError;
import io.reactivex.internal.operators.single.SingleFlatMap;
import io.reactivex.internal.operators.single.SingleFlatMapCompletable;
import io.reactivex.internal.operators.single.SingleFlatMapIterableFlowable;
import io.reactivex.internal.operators.single.SingleFlatMapIterableObservable;
import io.reactivex.internal.operators.single.SingleFlatMapMaybe;
import io.reactivex.internal.operators.single.SingleFromCallable;
import io.reactivex.internal.operators.single.SingleFromPublisher;
import io.reactivex.internal.operators.single.SingleFromUnsafeSource;
import io.reactivex.internal.operators.single.SingleHide;
import io.reactivex.internal.operators.single.SingleInternalHelper;
import io.reactivex.internal.operators.single.SingleJust;
import io.reactivex.internal.operators.single.SingleLift;
import io.reactivex.internal.operators.single.SingleMap;
import io.reactivex.internal.operators.single.SingleNever;
import io.reactivex.internal.operators.single.SingleObserveOn;
import io.reactivex.internal.operators.single.SingleOnErrorReturn;
import io.reactivex.internal.operators.single.SingleResumeNext;
import io.reactivex.internal.operators.single.SingleSubscribeOn;
import io.reactivex.internal.operators.single.SingleTakeUntil;
import io.reactivex.internal.operators.single.SingleTimeout;
import io.reactivex.internal.operators.single.SingleTimer;
import io.reactivex.internal.operators.single.SingleToFlowable;
import io.reactivex.internal.operators.single.SingleToObservable;
import io.reactivex.internal.operators.single.SingleUnsubscribeOn;
import io.reactivex.internal.operators.single.SingleUsing;
import io.reactivex.internal.operators.single.SingleZipArray;
import io.reactivex.internal.operators.single.SingleZipIterable;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;

public abstract class Single<T extends Object> implements SingleSource<T> {

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Single<T> amb(Iterable<? extends SingleSource<? extends T>> var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      return RxJavaPlugins.onAssembly(new SingleAmb((SingleSource[])null, var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Single<T> ambArray(SingleSource<? extends T> ... var0) {
      return var0.length == 0?error(SingleInternalHelper.emptyThrower()):(var0.length == 1?wrap(var0[0]):RxJavaPlugins.onAssembly(new SingleAmb(var0, (Iterable)null)));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> concat(SingleSource<? extends T> var0, SingleSource<? extends T> var1) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      return concat((Publisher)Flowable.fromArray(new SingleSource[]{var0, var1}));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> concat(SingleSource<? extends T> var0, SingleSource<? extends T> var1, SingleSource<? extends T> var2) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      return concat((Publisher)Flowable.fromArray(new SingleSource[]{var0, var1, var2}));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> concat(SingleSource<? extends T> var0, SingleSource<? extends T> var1, SingleSource<? extends T> var2, SingleSource<? extends T> var3) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      return concat((Publisher)Flowable.fromArray(new SingleSource[]{var0, var1, var2, var3}));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> concat(Iterable<? extends SingleSource<? extends T>> var0) {
      return concat((Publisher)Flowable.fromIterable(var0));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> concat(Publisher<? extends SingleSource<? extends T>> var0) {
      return concat(var0, 2);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> concat(Publisher<? extends SingleSource<? extends T>> var0, int var1) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      ObjectHelper.verifyPositive(var1, "prefetch");
      return RxJavaPlugins.onAssembly(new FlowableConcatMapPublisher(var0, SingleInternalHelper.toFlowable(), var1, ErrorMode.IMMEDIATE));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> concat(ObservableSource<? extends SingleSource<? extends T>> var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      return RxJavaPlugins.onAssembly(new ObservableConcatMap(var0, SingleInternalHelper.toObservable(), 2, ErrorMode.IMMEDIATE));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> concatArray(SingleSource<? extends T> ... var0) {
      return RxJavaPlugins.onAssembly(new FlowableConcatMap(Flowable.fromArray(var0), SingleInternalHelper.toFlowable(), 2, ErrorMode.BOUNDARY));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Single<T> create(SingleOnSubscribe<T> var0) {
      ObjectHelper.requireNonNull(var0, "source is null");
      return RxJavaPlugins.onAssembly(new SingleCreate(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Single<T> defer(Callable<? extends SingleSource<? extends T>> var0) {
      ObjectHelper.requireNonNull(var0, "singleSupplier is null");
      return RxJavaPlugins.onAssembly(new SingleDefer(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Single<Boolean> equals(SingleSource<? extends T> var0, SingleSource<? extends T> var1) {
      ObjectHelper.requireNonNull(var0, "first is null");
      ObjectHelper.requireNonNull(var1, "second is null");
      return RxJavaPlugins.onAssembly(new SingleEquals(var0, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Single<T> error(Throwable var0) {
      ObjectHelper.requireNonNull(var0, "error is null");
      return error(Functions.justCallable(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Single<T> error(Callable<? extends Throwable> var0) {
      ObjectHelper.requireNonNull(var0, "errorSupplier is null");
      return RxJavaPlugins.onAssembly(new SingleError(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Single<T> fromCallable(Callable<? extends T> var0) {
      ObjectHelper.requireNonNull(var0, "callable is null");
      return RxJavaPlugins.onAssembly(new SingleFromCallable(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Single<T> fromFuture(Future<? extends T> var0) {
      return toSingle(Flowable.fromFuture(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Single<T> fromFuture(Future<? extends T> var0, long var1, TimeUnit var3) {
      return toSingle(Flowable.fromFuture(var0, var1, var3));
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public static <T extends Object> Single<T> fromFuture(Future<? extends T> var0, long var1, TimeUnit var3, Scheduler var4) {
      return toSingle(Flowable.fromFuture(var0, var1, var3, var4));
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public static <T extends Object> Single<T> fromFuture(Future<? extends T> var0, Scheduler var1) {
      return toSingle(Flowable.fromFuture(var0, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Single<T> fromObservable(ObservableSource<? extends T> var0) {
      ObjectHelper.requireNonNull(var0, "observableSource is null");
      return RxJavaPlugins.onAssembly(new ObservableSingleSingle(var0, (Object)null));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Single<T> fromPublisher(Publisher<? extends T> var0) {
      ObjectHelper.requireNonNull(var0, "publisher is null");
      return RxJavaPlugins.onAssembly(new SingleFromPublisher(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Single<T> just(T var0) {
      ObjectHelper.requireNonNull(var0, "value is null");
      return RxJavaPlugins.onAssembly(new SingleJust(var0));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> merge(SingleSource<? extends T> var0, SingleSource<? extends T> var1) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      return merge((Publisher)Flowable.fromArray(new SingleSource[]{var0, var1}));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> merge(SingleSource<? extends T> var0, SingleSource<? extends T> var1, SingleSource<? extends T> var2) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      return merge((Publisher)Flowable.fromArray(new SingleSource[]{var0, var1, var2}));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> merge(SingleSource<? extends T> var0, SingleSource<? extends T> var1, SingleSource<? extends T> var2, SingleSource<? extends T> var3) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      return merge((Publisher)Flowable.fromArray(new SingleSource[]{var0, var1, var2, var3}));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> merge(Iterable<? extends SingleSource<? extends T>> var0) {
      return merge((Publisher)Flowable.fromIterable(var0));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> merge(Publisher<? extends SingleSource<? extends T>> var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      return RxJavaPlugins.onAssembly(new FlowableFlatMapPublisher(var0, SingleInternalHelper.toFlowable(), false, Integer.MAX_VALUE, Flowable.bufferSize()));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Single<T> merge(SingleSource<? extends SingleSource<? extends T>> var0) {
      ObjectHelper.requireNonNull(var0, "source is null");
      return RxJavaPlugins.onAssembly(new SingleFlatMap(var0, Functions.identity()));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Single<T> never() {
      return RxJavaPlugins.onAssembly(SingleNever.INSTANCE);
   }

   private Single<T> timeout0(long var1, TimeUnit var3, Scheduler var4, SingleSource<? extends T> var5) {
      ObjectHelper.requireNonNull(var3, "unit is null");
      ObjectHelper.requireNonNull(var4, "scheduler is null");
      return RxJavaPlugins.onAssembly(new SingleTimeout(this, var1, var3, var4, var5));
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public static Single<Long> timer(long var0, TimeUnit var2) {
      return timer(var0, var2, Schedulers.computation());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public static Single<Long> timer(long var0, TimeUnit var2, Scheduler var3) {
      ObjectHelper.requireNonNull(var2, "unit is null");
      ObjectHelper.requireNonNull(var3, "scheduler is null");
      return RxJavaPlugins.onAssembly(new SingleTimer(var0, var2, var3));
   }

   private static <T extends Object> Single<T> toSingle(Flowable<T> var0) {
      return RxJavaPlugins.onAssembly(new FlowableSingleSingle(var0, (Object)null));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Single<T> unsafeCreate(SingleSource<T> var0) {
      ObjectHelper.requireNonNull(var0, "onSubscribe is null");
      if(var0 instanceof Single) {
         throw new IllegalArgumentException("unsafeCreate(Single) should be upgraded");
      } else {
         return RxJavaPlugins.onAssembly(new SingleFromUnsafeSource(var0));
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, U extends Object> Single<T> using(Callable<U> var0, Function<? super U, ? extends SingleSource<? extends T>> var1, Consumer<? super U> var2) {
      return using(var0, var1, var2, true);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, U extends Object> Single<T> using(Callable<U> var0, Function<? super U, ? extends SingleSource<? extends T>> var1, Consumer<? super U> var2, boolean var3) {
      ObjectHelper.requireNonNull(var0, "resourceSupplier is null");
      ObjectHelper.requireNonNull(var1, "singleFunction is null");
      ObjectHelper.requireNonNull(var2, "disposer is null");
      return RxJavaPlugins.onAssembly(new SingleUsing(var0, var1, var2, var3));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Single<T> wrap(SingleSource<T> var0) {
      ObjectHelper.requireNonNull(var0, "source is null");
      return var0 instanceof Single?RxJavaPlugins.onAssembly((Single)var0):RxJavaPlugins.onAssembly(new SingleFromUnsafeSource(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, T5 extends Object, T6 extends Object, T7 extends Object, T8 extends Object, T9 extends Object, R extends Object> Single<R> zip(SingleSource<? extends T1> var0, SingleSource<? extends T2> var1, SingleSource<? extends T3> var2, SingleSource<? extends T4> var3, SingleSource<? extends T5> var4, SingleSource<? extends T6> var5, SingleSource<? extends T7> var6, SingleSource<? extends T8> var7, SingleSource<? extends T9> var8, Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> var9) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      ObjectHelper.requireNonNull(var4, "source5 is null");
      ObjectHelper.requireNonNull(var5, "source6 is null");
      ObjectHelper.requireNonNull(var6, "source7 is null");
      ObjectHelper.requireNonNull(var7, "source8 is null");
      ObjectHelper.requireNonNull(var8, "source9 is null");
      return zipArray(Functions.toFunction(var9), new SingleSource[]{var0, var1, var2, var3, var4, var5, var6, var7, var8});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, T5 extends Object, T6 extends Object, T7 extends Object, T8 extends Object, R extends Object> Single<R> zip(SingleSource<? extends T1> var0, SingleSource<? extends T2> var1, SingleSource<? extends T3> var2, SingleSource<? extends T4> var3, SingleSource<? extends T5> var4, SingleSource<? extends T6> var5, SingleSource<? extends T7> var6, SingleSource<? extends T8> var7, Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> var8) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      ObjectHelper.requireNonNull(var4, "source5 is null");
      ObjectHelper.requireNonNull(var5, "source6 is null");
      ObjectHelper.requireNonNull(var6, "source7 is null");
      ObjectHelper.requireNonNull(var7, "source8 is null");
      return zipArray(Functions.toFunction(var8), new SingleSource[]{var0, var1, var2, var3, var4, var5, var6, var7});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, T5 extends Object, T6 extends Object, T7 extends Object, R extends Object> Single<R> zip(SingleSource<? extends T1> var0, SingleSource<? extends T2> var1, SingleSource<? extends T3> var2, SingleSource<? extends T4> var3, SingleSource<? extends T5> var4, SingleSource<? extends T6> var5, SingleSource<? extends T7> var6, Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> var7) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      ObjectHelper.requireNonNull(var4, "source5 is null");
      ObjectHelper.requireNonNull(var5, "source6 is null");
      ObjectHelper.requireNonNull(var6, "source7 is null");
      return zipArray(Functions.toFunction(var7), new SingleSource[]{var0, var1, var2, var3, var4, var5, var6});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, T5 extends Object, T6 extends Object, R extends Object> Single<R> zip(SingleSource<? extends T1> var0, SingleSource<? extends T2> var1, SingleSource<? extends T3> var2, SingleSource<? extends T4> var3, SingleSource<? extends T5> var4, SingleSource<? extends T6> var5, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> var6) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      ObjectHelper.requireNonNull(var4, "source5 is null");
      ObjectHelper.requireNonNull(var5, "source6 is null");
      return zipArray(Functions.toFunction(var6), new SingleSource[]{var0, var1, var2, var3, var4, var5});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, T5 extends Object, R extends Object> Single<R> zip(SingleSource<? extends T1> var0, SingleSource<? extends T2> var1, SingleSource<? extends T3> var2, SingleSource<? extends T4> var3, SingleSource<? extends T5> var4, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> var5) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      ObjectHelper.requireNonNull(var4, "source5 is null");
      return zipArray(Functions.toFunction(var5), new SingleSource[]{var0, var1, var2, var3, var4});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, R extends Object> Single<R> zip(SingleSource<? extends T1> var0, SingleSource<? extends T2> var1, SingleSource<? extends T3> var2, SingleSource<? extends T4> var3, Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> var4) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      return zipArray(Functions.toFunction(var4), new SingleSource[]{var0, var1, var2, var3});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, R extends Object> Single<R> zip(SingleSource<? extends T1> var0, SingleSource<? extends T2> var1, SingleSource<? extends T3> var2, Function3<? super T1, ? super T2, ? super T3, ? extends R> var3) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      return zipArray(Functions.toFunction(var3), new SingleSource[]{var0, var1, var2});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, R extends Object> Single<R> zip(SingleSource<? extends T1> var0, SingleSource<? extends T2> var1, BiFunction<? super T1, ? super T2, ? extends R> var2) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      return zipArray(Functions.toFunction(var2), new SingleSource[]{var0, var1});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Single<R> zip(Iterable<? extends SingleSource<? extends T>> var0, Function<? super Object[], ? extends R> var1) {
      ObjectHelper.requireNonNull(var1, "zipper is null");
      ObjectHelper.requireNonNull(var0, "sources is null");
      return RxJavaPlugins.onAssembly(new SingleZipIterable(var0, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Single<R> zipArray(Function<? super Object[], ? extends R> var0, SingleSource<? extends T> ... var1) {
      ObjectHelper.requireNonNull(var0, "zipper is null");
      ObjectHelper.requireNonNull(var1, "sources is null");
      return var1.length == 0?error((Throwable)(new NoSuchElementException())):RxJavaPlugins.onAssembly(new SingleZipArray(var1, var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> ambWith(SingleSource<? extends T> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return ambArray(new SingleSource[]{this, var1});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   @Experimental
   public final <R extends Object> R as(@NonNull SingleConverter<T, ? extends R> var1) {
      return ((SingleConverter)ObjectHelper.requireNonNull(var1, "converter is null")).apply(this);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final T blockingGet() {
      BlockingMultiObserver var1 = new BlockingMultiObserver();
      this.subscribe((SingleObserver)var1);
      return var1.blockingGet();
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> cache() {
      return RxJavaPlugins.onAssembly(new SingleCache(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Single<U> cast(Class<? extends U> var1) {
      ObjectHelper.requireNonNull(var1, "clazz is null");
      return this.map(Functions.castFunction(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Single<R> compose(SingleTransformer<? super T, ? extends R> var1) {
      return wrap(((SingleTransformer)ObjectHelper.requireNonNull(var1, "transformer is null")).apply(this));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> concatWith(SingleSource<? extends T> var1) {
      return concat(this, var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<Boolean> contains(Object var1) {
      return this.contains(var1, ObjectHelper.equalsPredicate());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<Boolean> contains(Object var1, BiPredicate<Object, Object> var2) {
      ObjectHelper.requireNonNull(var1, "value is null");
      ObjectHelper.requireNonNull(var2, "comparer is null");
      return RxJavaPlugins.onAssembly(new SingleContains(this, var1, var2));
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Single<T> delay(long var1, TimeUnit var3) {
      return this.delay(var1, var3, Schedulers.computation(), false);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Single<T> delay(long var1, TimeUnit var3, Scheduler var4) {
      return this.delay(var1, var3, var4, false);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   @Experimental
   public final Single<T> delay(long var1, TimeUnit var3, Scheduler var4, boolean var5) {
      ObjectHelper.requireNonNull(var3, "unit is null");
      ObjectHelper.requireNonNull(var4, "scheduler is null");
      return RxJavaPlugins.onAssembly(new SingleDelay(this, var1, var3, var4, var5));
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   @Experimental
   public final Single<T> delay(long var1, TimeUnit var3, boolean var4) {
      return this.delay(var1, var3, Schedulers.computation(), var4);
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Single<T> delaySubscription(long var1, TimeUnit var3) {
      return this.delaySubscription(var1, var3, Schedulers.computation());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Single<T> delaySubscription(long var1, TimeUnit var3, Scheduler var4) {
      return this.delaySubscription((ObservableSource)Observable.timer(var1, var3, var4));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> delaySubscription(CompletableSource var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return RxJavaPlugins.onAssembly(new SingleDelayWithCompletable(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Single<T> delaySubscription(ObservableSource<U> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return RxJavaPlugins.onAssembly(new SingleDelayWithObservable(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Single<T> delaySubscription(SingleSource<U> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return RxJavaPlugins.onAssembly(new SingleDelayWithSingle(this, var1));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Single<T> delaySubscription(Publisher<U> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return RxJavaPlugins.onAssembly(new SingleDelayWithPublisher(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> doAfterSuccess(Consumer<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "doAfterSuccess is null");
      return RxJavaPlugins.onAssembly(new SingleDoAfterSuccess(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> doAfterTerminate(Action var1) {
      ObjectHelper.requireNonNull(var1, "onAfterTerminate is null");
      return RxJavaPlugins.onAssembly(new SingleDoAfterTerminate(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> doFinally(Action var1) {
      ObjectHelper.requireNonNull(var1, "onFinally is null");
      return RxJavaPlugins.onAssembly(new SingleDoFinally(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> doOnDispose(Action var1) {
      ObjectHelper.requireNonNull(var1, "onDispose is null");
      return RxJavaPlugins.onAssembly(new SingleDoOnDispose(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> doOnError(Consumer<? super Throwable> var1) {
      ObjectHelper.requireNonNull(var1, "onError is null");
      return RxJavaPlugins.onAssembly(new SingleDoOnError(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> doOnEvent(BiConsumer<? super T, ? super Throwable> var1) {
      ObjectHelper.requireNonNull(var1, "onEvent is null");
      return RxJavaPlugins.onAssembly(new SingleDoOnEvent(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> doOnSubscribe(Consumer<? super Disposable> var1) {
      ObjectHelper.requireNonNull(var1, "onSubscribe is null");
      return RxJavaPlugins.onAssembly(new SingleDoOnSubscribe(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> doOnSuccess(Consumer<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "onSuccess is null");
      return RxJavaPlugins.onAssembly(new SingleDoOnSuccess(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> filter(Predicate<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "predicate is null");
      return RxJavaPlugins.onAssembly(new MaybeFilterSingle(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Single<R> flatMap(Function<? super T, ? extends SingleSource<? extends R>> var1) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      return RxJavaPlugins.onAssembly(new SingleFlatMap(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable flatMapCompletable(Function<? super T, ? extends CompletableSource> var1) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      return RxJavaPlugins.onAssembly(new SingleFlatMapCompletable(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Maybe<R> flatMapMaybe(Function<? super T, ? extends MaybeSource<? extends R>> var1) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      return RxJavaPlugins.onAssembly(new SingleFlatMapMaybe(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> flatMapObservable(Function<? super T, ? extends ObservableSource<? extends R>> var1) {
      return this.toObservable().flatMap(var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> flatMapPublisher(Function<? super T, ? extends Publisher<? extends R>> var1) {
      return this.toFlowable().flatMap(var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Flowable<U> flattenAsFlowable(Function<? super T, ? extends Iterable<? extends U>> var1) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      return RxJavaPlugins.onAssembly(new SingleFlatMapIterableFlowable(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Observable<U> flattenAsObservable(Function<? super T, ? extends Iterable<? extends U>> var1) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      return RxJavaPlugins.onAssembly(new SingleFlatMapIterableObservable(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> hide() {
      return RxJavaPlugins.onAssembly(new SingleHide(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Single<R> lift(SingleOperator<? extends R, ? super T> var1) {
      ObjectHelper.requireNonNull(var1, "onLift is null");
      return RxJavaPlugins.onAssembly(new SingleLift(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Single<R> map(Function<? super T, ? extends R> var1) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      return RxJavaPlugins.onAssembly(new SingleMap(this, var1));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> mergeWith(SingleSource<? extends T> var1) {
      return merge(this, var1);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Single<T> observeOn(Scheduler var1) {
      ObjectHelper.requireNonNull(var1, "scheduler is null");
      return RxJavaPlugins.onAssembly(new SingleObserveOn(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> onErrorResumeNext(Single<? extends T> var1) {
      ObjectHelper.requireNonNull(var1, "resumeSingleInCaseOfError is null");
      return this.onErrorResumeNext(Functions.justFunction(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> onErrorResumeNext(Function<? super Throwable, ? extends SingleSource<? extends T>> var1) {
      ObjectHelper.requireNonNull(var1, "resumeFunctionInCaseOfError is null");
      return RxJavaPlugins.onAssembly(new SingleResumeNext(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> onErrorReturn(Function<Throwable, ? extends T> var1) {
      ObjectHelper.requireNonNull(var1, "resumeFunction is null");
      return RxJavaPlugins.onAssembly(new SingleOnErrorReturn(this, var1, (Object)null));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> onErrorReturnItem(T var1) {
      ObjectHelper.requireNonNull(var1, "value is null");
      return RxJavaPlugins.onAssembly(new SingleOnErrorReturn(this, (Function)null, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   @Experimental
   public final Single<T> onTerminateDetach() {
      return RxJavaPlugins.onAssembly(new SingleDetach(this));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> repeat() {
      return this.toFlowable().repeat();
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> repeat(long var1) {
      return this.toFlowable().repeat(var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> repeatUntil(BooleanSupplier var1) {
      return this.toFlowable().repeatUntil(var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> repeatWhen(Function<? super Flowable<Object>, ? extends Publisher<?>> var1) {
      return this.toFlowable().repeatWhen(var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> retry() {
      return toSingle(this.toFlowable().retry());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> retry(long var1) {
      return toSingle(this.toFlowable().retry(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> retry(BiPredicate<? super Integer, ? super Throwable> var1) {
      return toSingle(this.toFlowable().retry(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> retry(Predicate<? super Throwable> var1) {
      return toSingle(this.toFlowable().retry(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> retryWhen(Function<? super Flowable<Throwable>, ? extends Publisher<?>> var1) {
      return toSingle(this.toFlowable().retryWhen(var1));
   }

   @SchedulerSupport("none")
   public final Disposable subscribe() {
      return this.subscribe(Functions.emptyConsumer(), Functions.ON_ERROR_MISSING);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Disposable subscribe(BiConsumer<? super T, ? super Throwable> var1) {
      ObjectHelper.requireNonNull(var1, "onCallback is null");
      BiConsumerSingleObserver var2 = new BiConsumerSingleObserver(var1);
      this.subscribe((SingleObserver)var2);
      return var2;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Disposable subscribe(Consumer<? super T> var1) {
      return this.subscribe(var1, Functions.ON_ERROR_MISSING);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Disposable subscribe(Consumer<? super T> var1, Consumer<? super Throwable> var2) {
      ObjectHelper.requireNonNull(var1, "onSuccess is null");
      ObjectHelper.requireNonNull(var2, "onError is null");
      ConsumerSingleObserver var3 = new ConsumerSingleObserver(var1, var2);
      this.subscribe((SingleObserver)var3);
      return var3;
   }

   @SchedulerSupport("none")
   public final void subscribe(SingleObserver<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "subscriber is null");
      var1 = RxJavaPlugins.onSubscribe(this, var1);
      ObjectHelper.requireNonNull(var1, "subscriber returned by the RxJavaPlugins hook is null");

      try {
         this.subscribeActual(var1);
      } catch (NullPointerException var3) {
         throw var3;
      } catch (Throwable var4) {
         Exceptions.throwIfFatal(var4);
         NullPointerException var2 = new NullPointerException("subscribeActual failed");
         var2.initCause(var4);
         throw var2;
      }
   }

   public abstract void subscribeActual(@NonNull SingleObserver<? super T> var1);

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Single<T> subscribeOn(Scheduler var1) {
      ObjectHelper.requireNonNull(var1, "scheduler is null");
      return RxJavaPlugins.onAssembly(new SingleSubscribeOn(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <E extends Object & SingleObserver<? super T>> E subscribeWith(E var1) {
      this.subscribe(var1);
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> takeUntil(CompletableSource var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return this.takeUntil((Publisher)(new CompletableToFlowable(var1)));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <E extends Object> Single<T> takeUntil(SingleSource<? extends E> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return this.takeUntil((Publisher)(new SingleToFlowable(var1)));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <E extends Object> Single<T> takeUntil(Publisher<E> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return RxJavaPlugins.onAssembly(new SingleTakeUntil(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final TestObserver<T> test() {
      TestObserver var1 = new TestObserver();
      this.subscribe((SingleObserver)var1);
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final TestObserver<T> test(boolean var1) {
      TestObserver var2 = new TestObserver();
      if(var1) {
         var2.cancel();
      }

      this.subscribe((SingleObserver)var2);
      return var2;
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Single<T> timeout(long var1, TimeUnit var3) {
      return this.timeout0(var1, var3, Schedulers.computation(), (SingleSource)null);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Single<T> timeout(long var1, TimeUnit var3, Scheduler var4) {
      return this.timeout0(var1, var3, var4, (SingleSource)null);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Single<T> timeout(long var1, TimeUnit var3, Scheduler var4, SingleSource<? extends T> var5) {
      ObjectHelper.requireNonNull(var5, "other is null");
      return this.timeout0(var1, var3, var4, var5);
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Single<T> timeout(long var1, TimeUnit var3, SingleSource<? extends T> var4) {
      ObjectHelper.requireNonNull(var4, "other is null");
      return this.timeout0(var1, var3, Schedulers.computation(), var4);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> R to(Function<? super Single<T>, R> var1) {
      try {
         Object var3 = ((Function)ObjectHelper.requireNonNull(var1, "convert is null")).apply(this);
         return var3;
      } catch (Throwable var2) {
         Exceptions.throwIfFatal(var2);
         throw ExceptionHelper.wrapOrThrow(var2);
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable toCompletable() {
      return RxJavaPlugins.onAssembly(new CompletableFromSingle(this));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> toFlowable() {
      return this instanceof FuseToFlowable?((FuseToFlowable)this).fuseToFlowable():RxJavaPlugins.onAssembly(new SingleToFlowable(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Future<T> toFuture() {
      return (Future)this.subscribeWith(new FutureSingleObserver());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> toMaybe() {
      return this instanceof FuseToMaybe?((FuseToMaybe)this).fuseToMaybe():RxJavaPlugins.onAssembly(new MaybeFromSingle(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> toObservable() {
      return this instanceof FuseToObservable?((FuseToObservable)this).fuseToObservable():RxJavaPlugins.onAssembly(new SingleToObservable(this));
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   @Experimental
   public final Single<T> unsubscribeOn(Scheduler var1) {
      ObjectHelper.requireNonNull(var1, "scheduler is null");
      return RxJavaPlugins.onAssembly(new SingleUnsubscribeOn(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, R extends Object> Single<R> zipWith(SingleSource<U> var1, BiFunction<? super T, ? super U, ? extends R> var2) {
      return zip(this, var1, var2);
   }
}
