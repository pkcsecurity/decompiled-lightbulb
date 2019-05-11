package io.reactivex;

import io.reactivex.CompletableConverter;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.CompletableOperator;
import io.reactivex.CompletableSource;
import io.reactivex.CompletableTransformer;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.BackpressureKind;
import io.reactivex.annotations.BackpressureSupport;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.Experimental;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.fuseable.FuseToMaybe;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.internal.observers.BlockingMultiObserver;
import io.reactivex.internal.observers.CallbackCompletableObserver;
import io.reactivex.internal.observers.EmptyCompletableObserver;
import io.reactivex.internal.operators.completable.CompletableAmb;
import io.reactivex.internal.operators.completable.CompletableCache;
import io.reactivex.internal.operators.completable.CompletableConcat;
import io.reactivex.internal.operators.completable.CompletableConcatArray;
import io.reactivex.internal.operators.completable.CompletableConcatIterable;
import io.reactivex.internal.operators.completable.CompletableCreate;
import io.reactivex.internal.operators.completable.CompletableDefer;
import io.reactivex.internal.operators.completable.CompletableDelay;
import io.reactivex.internal.operators.completable.CompletableDetach;
import io.reactivex.internal.operators.completable.CompletableDisposeOn;
import io.reactivex.internal.operators.completable.CompletableDoFinally;
import io.reactivex.internal.operators.completable.CompletableDoOnEvent;
import io.reactivex.internal.operators.completable.CompletableEmpty;
import io.reactivex.internal.operators.completable.CompletableError;
import io.reactivex.internal.operators.completable.CompletableErrorSupplier;
import io.reactivex.internal.operators.completable.CompletableFromAction;
import io.reactivex.internal.operators.completable.CompletableFromCallable;
import io.reactivex.internal.operators.completable.CompletableFromObservable;
import io.reactivex.internal.operators.completable.CompletableFromPublisher;
import io.reactivex.internal.operators.completable.CompletableFromRunnable;
import io.reactivex.internal.operators.completable.CompletableFromSingle;
import io.reactivex.internal.operators.completable.CompletableFromUnsafeSource;
import io.reactivex.internal.operators.completable.CompletableHide;
import io.reactivex.internal.operators.completable.CompletableLift;
import io.reactivex.internal.operators.completable.CompletableMerge;
import io.reactivex.internal.operators.completable.CompletableMergeArray;
import io.reactivex.internal.operators.completable.CompletableMergeDelayErrorArray;
import io.reactivex.internal.operators.completable.CompletableMergeDelayErrorIterable;
import io.reactivex.internal.operators.completable.CompletableMergeIterable;
import io.reactivex.internal.operators.completable.CompletableNever;
import io.reactivex.internal.operators.completable.CompletableObserveOn;
import io.reactivex.internal.operators.completable.CompletableOnErrorComplete;
import io.reactivex.internal.operators.completable.CompletablePeek;
import io.reactivex.internal.operators.completable.CompletableResumeNext;
import io.reactivex.internal.operators.completable.CompletableSubscribeOn;
import io.reactivex.internal.operators.completable.CompletableTimeout;
import io.reactivex.internal.operators.completable.CompletableTimer;
import io.reactivex.internal.operators.completable.CompletableToFlowable;
import io.reactivex.internal.operators.completable.CompletableToObservable;
import io.reactivex.internal.operators.completable.CompletableToSingle;
import io.reactivex.internal.operators.completable.CompletableUsing;
import io.reactivex.internal.operators.flowable.FlowableDelaySubscriptionOther;
import io.reactivex.internal.operators.maybe.MaybeDelayWithCompletable;
import io.reactivex.internal.operators.maybe.MaybeFromCompletable;
import io.reactivex.internal.operators.observable.ObservableDelaySubscriptionOther;
import io.reactivex.internal.operators.single.SingleDelayWithCompletable;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;

public abstract class Completable implements CompletableSource {

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable amb(Iterable<? extends CompletableSource> var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      return RxJavaPlugins.onAssembly(new CompletableAmb((CompletableSource[])null, var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable ambArray(CompletableSource ... var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      return var0.length == 0?complete():(var0.length == 1?wrap(var0[0]):RxJavaPlugins.onAssembly(new CompletableAmb(var0, (Iterable)null)));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable complete() {
      return RxJavaPlugins.onAssembly(CompletableEmpty.INSTANCE);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable concat(Iterable<? extends CompletableSource> var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      return RxJavaPlugins.onAssembly(new CompletableConcatIterable(var0));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable concat(Publisher<? extends CompletableSource> var0) {
      return concat(var0, 2);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable concat(Publisher<? extends CompletableSource> var0, int var1) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      ObjectHelper.verifyPositive(var1, "prefetch");
      return RxJavaPlugins.onAssembly(new CompletableConcat(var0, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable concatArray(CompletableSource ... var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      return var0.length == 0?complete():(var0.length == 1?wrap(var0[0]):RxJavaPlugins.onAssembly(new CompletableConcatArray(var0)));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable create(CompletableOnSubscribe var0) {
      ObjectHelper.requireNonNull(var0, "source is null");
      return RxJavaPlugins.onAssembly(new CompletableCreate(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable defer(Callable<? extends CompletableSource> var0) {
      ObjectHelper.requireNonNull(var0, "completableSupplier");
      return RxJavaPlugins.onAssembly(new CompletableDefer(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   private Completable doOnLifecycle(Consumer<? super Disposable> var1, Consumer<? super Throwable> var2, Action var3, Action var4, Action var5, Action var6) {
      ObjectHelper.requireNonNull(var1, "onSubscribe is null");
      ObjectHelper.requireNonNull(var2, "onError is null");
      ObjectHelper.requireNonNull(var3, "onComplete is null");
      ObjectHelper.requireNonNull(var4, "onTerminate is null");
      ObjectHelper.requireNonNull(var5, "onAfterTerminate is null");
      ObjectHelper.requireNonNull(var6, "onDispose is null");
      return RxJavaPlugins.onAssembly(new CompletablePeek(this, var1, var2, var3, var4, var5, var6));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable error(Throwable var0) {
      ObjectHelper.requireNonNull(var0, "error is null");
      return RxJavaPlugins.onAssembly(new CompletableError(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable error(Callable<? extends Throwable> var0) {
      ObjectHelper.requireNonNull(var0, "errorSupplier is null");
      return RxJavaPlugins.onAssembly(new CompletableErrorSupplier(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable fromAction(Action var0) {
      ObjectHelper.requireNonNull(var0, "run is null");
      return RxJavaPlugins.onAssembly(new CompletableFromAction(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable fromCallable(Callable<?> var0) {
      ObjectHelper.requireNonNull(var0, "callable is null");
      return RxJavaPlugins.onAssembly(new CompletableFromCallable(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable fromFuture(Future<?> var0) {
      ObjectHelper.requireNonNull(var0, "future is null");
      return fromAction(Functions.futureAction(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Completable fromObservable(ObservableSource<T> var0) {
      ObjectHelper.requireNonNull(var0, "observable is null");
      return RxJavaPlugins.onAssembly(new CompletableFromObservable(var0));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Completable fromPublisher(Publisher<T> var0) {
      ObjectHelper.requireNonNull(var0, "publisher is null");
      return RxJavaPlugins.onAssembly(new CompletableFromPublisher(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable fromRunnable(Runnable var0) {
      ObjectHelper.requireNonNull(var0, "run is null");
      return RxJavaPlugins.onAssembly(new CompletableFromRunnable(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Completable fromSingle(SingleSource<T> var0) {
      ObjectHelper.requireNonNull(var0, "single is null");
      return RxJavaPlugins.onAssembly(new CompletableFromSingle(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable merge(Iterable<? extends CompletableSource> var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      return RxJavaPlugins.onAssembly(new CompletableMergeIterable(var0));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable merge(Publisher<? extends CompletableSource> var0) {
      return merge0(var0, Integer.MAX_VALUE, false);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable merge(Publisher<? extends CompletableSource> var0, int var1) {
      return merge0(var0, var1, false);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   private static Completable merge0(Publisher<? extends CompletableSource> var0, int var1, boolean var2) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      ObjectHelper.verifyPositive(var1, "maxConcurrency");
      return RxJavaPlugins.onAssembly(new CompletableMerge(var0, var1, var2));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable mergeArray(CompletableSource ... var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      return var0.length == 0?complete():(var0.length == 1?wrap(var0[0]):RxJavaPlugins.onAssembly(new CompletableMergeArray(var0)));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable mergeArrayDelayError(CompletableSource ... var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      return RxJavaPlugins.onAssembly(new CompletableMergeDelayErrorArray(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable mergeDelayError(Iterable<? extends CompletableSource> var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      return RxJavaPlugins.onAssembly(new CompletableMergeDelayErrorIterable(var0));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable mergeDelayError(Publisher<? extends CompletableSource> var0) {
      return merge0(var0, Integer.MAX_VALUE, true);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable mergeDelayError(Publisher<? extends CompletableSource> var0, int var1) {
      return merge0(var0, var1, true);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable never() {
      return RxJavaPlugins.onAssembly(CompletableNever.INSTANCE);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   private Completable timeout0(long var1, TimeUnit var3, Scheduler var4, CompletableSource var5) {
      ObjectHelper.requireNonNull(var3, "unit is null");
      ObjectHelper.requireNonNull(var4, "scheduler is null");
      return RxJavaPlugins.onAssembly(new CompletableTimeout(this, var1, var3, var4, var5));
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public static Completable timer(long var0, TimeUnit var2) {
      return timer(var0, var2, Schedulers.computation());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public static Completable timer(long var0, TimeUnit var2, Scheduler var3) {
      ObjectHelper.requireNonNull(var2, "unit is null");
      ObjectHelper.requireNonNull(var3, "scheduler is null");
      return RxJavaPlugins.onAssembly(new CompletableTimer(var0, var2, var3));
   }

   private static NullPointerException toNpe(Throwable var0) {
      NullPointerException var1 = new NullPointerException("Actually not, but can\'t pass out an exception otherwise...");
      var1.initCause(var0);
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable unsafeCreate(CompletableSource var0) {
      ObjectHelper.requireNonNull(var0, "source is null");
      if(var0 instanceof Completable) {
         throw new IllegalArgumentException("Use of unsafeCreate(Completable)!");
      } else {
         return RxJavaPlugins.onAssembly(new CompletableFromUnsafeSource(var0));
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <R extends Object> Completable using(Callable<R> var0, Function<? super R, ? extends CompletableSource> var1, Consumer<? super R> var2) {
      return using(var0, var1, var2, true);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <R extends Object> Completable using(Callable<R> var0, Function<? super R, ? extends CompletableSource> var1, Consumer<? super R> var2, boolean var3) {
      ObjectHelper.requireNonNull(var0, "resourceSupplier is null");
      ObjectHelper.requireNonNull(var1, "completableFunction is null");
      ObjectHelper.requireNonNull(var2, "disposer is null");
      return RxJavaPlugins.onAssembly(new CompletableUsing(var0, var1, var2, var3));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable wrap(CompletableSource var0) {
      ObjectHelper.requireNonNull(var0, "source is null");
      return var0 instanceof Completable?RxJavaPlugins.onAssembly((Completable)var0):RxJavaPlugins.onAssembly(new CompletableFromUnsafeSource(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable ambWith(CompletableSource var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return ambArray(new CompletableSource[]{this, var1});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable andThen(CompletableSource var1) {
      return this.concatWith(var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T extends Object> Flowable<T> andThen(Publisher<T> var1) {
      ObjectHelper.requireNonNull(var1, "next is null");
      return RxJavaPlugins.onAssembly(new FlowableDelaySubscriptionOther(var1, this.toFlowable()));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T extends Object> Maybe<T> andThen(MaybeSource<T> var1) {
      ObjectHelper.requireNonNull(var1, "next is null");
      return RxJavaPlugins.onAssembly(new MaybeDelayWithCompletable(var1, this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T extends Object> Observable<T> andThen(ObservableSource<T> var1) {
      ObjectHelper.requireNonNull(var1, "next is null");
      return RxJavaPlugins.onAssembly(new ObservableDelaySubscriptionOther(var1, this.toObservable()));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T extends Object> Single<T> andThen(SingleSource<T> var1) {
      ObjectHelper.requireNonNull(var1, "next is null");
      return RxJavaPlugins.onAssembly(new SingleDelayWithCompletable(var1, this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   @Experimental
   public final <R extends Object> R as(@NonNull CompletableConverter<? extends R> var1) {
      return ((CompletableConverter)ObjectHelper.requireNonNull(var1, "converter is null")).apply(this);
   }

   @SchedulerSupport("none")
   public final void blockingAwait() {
      BlockingMultiObserver var1 = new BlockingMultiObserver();
      this.subscribe((CompletableObserver)var1);
      var1.blockingGet();
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final boolean blockingAwait(long var1, TimeUnit var3) {
      ObjectHelper.requireNonNull(var3, "unit is null");
      BlockingMultiObserver var4 = new BlockingMultiObserver();
      this.subscribe((CompletableObserver)var4);
      return var4.blockingAwait(var1, var3);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Throwable blockingGet() {
      BlockingMultiObserver var1 = new BlockingMultiObserver();
      this.subscribe((CompletableObserver)var1);
      return var1.blockingGetError();
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Throwable blockingGet(long var1, TimeUnit var3) {
      ObjectHelper.requireNonNull(var3, "unit is null");
      BlockingMultiObserver var4 = new BlockingMultiObserver();
      this.subscribe((CompletableObserver)var4);
      return var4.blockingGetError(var1, var3);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable cache() {
      return RxJavaPlugins.onAssembly(new CompletableCache(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable compose(CompletableTransformer var1) {
      return wrap(((CompletableTransformer)ObjectHelper.requireNonNull(var1, "transformer is null")).apply(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable concatWith(CompletableSource var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return concatArray(new CompletableSource[]{this, var1});
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Completable delay(long var1, TimeUnit var3) {
      return this.delay(var1, var3, Schedulers.computation(), false);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Completable delay(long var1, TimeUnit var3, Scheduler var4) {
      return this.delay(var1, var3, var4, false);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Completable delay(long var1, TimeUnit var3, Scheduler var4, boolean var5) {
      ObjectHelper.requireNonNull(var3, "unit is null");
      ObjectHelper.requireNonNull(var4, "scheduler is null");
      return RxJavaPlugins.onAssembly(new CompletableDelay(this, var1, var3, var4, var5));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable doAfterTerminate(Action var1) {
      return this.doOnLifecycle(Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, var1, Functions.EMPTY_ACTION);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable doFinally(Action var1) {
      ObjectHelper.requireNonNull(var1, "onFinally is null");
      return RxJavaPlugins.onAssembly(new CompletableDoFinally(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable doOnComplete(Action var1) {
      return this.doOnLifecycle(Functions.emptyConsumer(), Functions.emptyConsumer(), var1, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable doOnDispose(Action var1) {
      return this.doOnLifecycle(Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable doOnError(Consumer<? super Throwable> var1) {
      return this.doOnLifecycle(Functions.emptyConsumer(), var1, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable doOnEvent(Consumer<? super Throwable> var1) {
      ObjectHelper.requireNonNull(var1, "onEvent is null");
      return RxJavaPlugins.onAssembly(new CompletableDoOnEvent(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable doOnSubscribe(Consumer<? super Disposable> var1) {
      return this.doOnLifecycle(var1, Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable doOnTerminate(Action var1) {
      return this.doOnLifecycle(Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, var1, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable hide() {
      return RxJavaPlugins.onAssembly(new CompletableHide(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable lift(CompletableOperator var1) {
      ObjectHelper.requireNonNull(var1, "onLift is null");
      return RxJavaPlugins.onAssembly(new CompletableLift(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable mergeWith(CompletableSource var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return mergeArray(new CompletableSource[]{this, var1});
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Completable observeOn(Scheduler var1) {
      ObjectHelper.requireNonNull(var1, "scheduler is null");
      return RxJavaPlugins.onAssembly(new CompletableObserveOn(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable onErrorComplete() {
      return this.onErrorComplete(Functions.alwaysTrue());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable onErrorComplete(Predicate<? super Throwable> var1) {
      ObjectHelper.requireNonNull(var1, "predicate is null");
      return RxJavaPlugins.onAssembly(new CompletableOnErrorComplete(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable onErrorResumeNext(Function<? super Throwable, ? extends CompletableSource> var1) {
      ObjectHelper.requireNonNull(var1, "errorMapper is null");
      return RxJavaPlugins.onAssembly(new CompletableResumeNext(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   @Experimental
   public final Completable onTerminateDetach() {
      return RxJavaPlugins.onAssembly(new CompletableDetach(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable repeat() {
      return fromPublisher(this.toFlowable().repeat());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable repeat(long var1) {
      return fromPublisher(this.toFlowable().repeat(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable repeatUntil(BooleanSupplier var1) {
      return fromPublisher(this.toFlowable().repeatUntil(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable repeatWhen(Function<? super Flowable<Object>, ? extends Publisher<?>> var1) {
      return fromPublisher(this.toFlowable().repeatWhen(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable retry() {
      return fromPublisher(this.toFlowable().retry());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable retry(long var1) {
      return fromPublisher(this.toFlowable().retry(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable retry(BiPredicate<? super Integer, ? super Throwable> var1) {
      return fromPublisher(this.toFlowable().retry(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable retry(Predicate<? super Throwable> var1) {
      return fromPublisher(this.toFlowable().retry(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable retryWhen(Function<? super Flowable<Throwable>, ? extends Publisher<?>> var1) {
      return fromPublisher(this.toFlowable().retryWhen(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable startWith(CompletableSource var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return concatArray(new CompletableSource[]{var1, this});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T extends Object> Flowable<T> startWith(Publisher<T> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return this.toFlowable().startWith(var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T extends Object> Observable<T> startWith(Observable<T> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return var1.concatWith(this.toObservable());
   }

   @SchedulerSupport("none")
   public final Disposable subscribe() {
      EmptyCompletableObserver var1 = new EmptyCompletableObserver();
      this.subscribe((CompletableObserver)var1);
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Disposable subscribe(Action var1) {
      ObjectHelper.requireNonNull(var1, "onComplete is null");
      CallbackCompletableObserver var2 = new CallbackCompletableObserver(var1);
      this.subscribe((CompletableObserver)var2);
      return var2;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Disposable subscribe(Action var1, Consumer<? super Throwable> var2) {
      ObjectHelper.requireNonNull(var2, "onError is null");
      ObjectHelper.requireNonNull(var1, "onComplete is null");
      CallbackCompletableObserver var3 = new CallbackCompletableObserver(var2, var1);
      this.subscribe((CompletableObserver)var3);
      return var3;
   }

   @SchedulerSupport("none")
   public final void subscribe(CompletableObserver var1) {
      ObjectHelper.requireNonNull(var1, "s is null");

      try {
         this.subscribeActual(RxJavaPlugins.onSubscribe(this, var1));
      } catch (NullPointerException var2) {
         throw var2;
      } catch (Throwable var3) {
         Exceptions.throwIfFatal(var3);
         RxJavaPlugins.onError(var3);
         throw toNpe(var3);
      }
   }

   public abstract void subscribeActual(CompletableObserver var1);

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Completable subscribeOn(Scheduler var1) {
      ObjectHelper.requireNonNull(var1, "scheduler is null");
      return RxJavaPlugins.onAssembly(new CompletableSubscribeOn(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <E extends Object & CompletableObserver> E subscribeWith(E var1) {
      this.subscribe(var1);
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final TestObserver<Void> test() {
      TestObserver var1 = new TestObserver();
      this.subscribe((CompletableObserver)var1);
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final TestObserver<Void> test(boolean var1) {
      TestObserver var2 = new TestObserver();
      if(var1) {
         var2.cancel();
      }

      this.subscribe((CompletableObserver)var2);
      return var2;
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Completable timeout(long var1, TimeUnit var3) {
      return this.timeout0(var1, var3, Schedulers.computation(), (CompletableSource)null);
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Completable timeout(long var1, TimeUnit var3, CompletableSource var4) {
      ObjectHelper.requireNonNull(var4, "other is null");
      return this.timeout0(var1, var3, Schedulers.computation(), var4);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Completable timeout(long var1, TimeUnit var3, Scheduler var4) {
      return this.timeout0(var1, var3, var4, (CompletableSource)null);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Completable timeout(long var1, TimeUnit var3, Scheduler var4, CompletableSource var5) {
      ObjectHelper.requireNonNull(var5, "other is null");
      return this.timeout0(var1, var3, var4, var5);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> U to(Function<? super Completable, U> var1) {
      try {
         Object var3 = ((Function)ObjectHelper.requireNonNull(var1, "converter is null")).apply(this);
         return var3;
      } catch (Throwable var2) {
         Exceptions.throwIfFatal(var2);
         throw ExceptionHelper.wrapOrThrow(var2);
      }
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T extends Object> Flowable<T> toFlowable() {
      return this instanceof FuseToFlowable?((FuseToFlowable)this).fuseToFlowable():RxJavaPlugins.onAssembly(new CompletableToFlowable(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T extends Object> Maybe<T> toMaybe() {
      return this instanceof FuseToMaybe?((FuseToMaybe)this).fuseToMaybe():RxJavaPlugins.onAssembly(new MaybeFromCompletable(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T extends Object> Observable<T> toObservable() {
      return this instanceof FuseToObservable?((FuseToObservable)this).fuseToObservable():RxJavaPlugins.onAssembly(new CompletableToObservable(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T extends Object> Single<T> toSingle(Callable<? extends T> var1) {
      ObjectHelper.requireNonNull(var1, "completionValueSupplier is null");
      return RxJavaPlugins.onAssembly(new CompletableToSingle(this, var1, (Object)null));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T extends Object> Single<T> toSingleDefault(T var1) {
      ObjectHelper.requireNonNull(var1, "completionValue is null");
      return RxJavaPlugins.onAssembly(new CompletableToSingle(this, (Callable)null, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Completable unsubscribeOn(Scheduler var1) {
      ObjectHelper.requireNonNull(var1, "scheduler is null");
      return RxJavaPlugins.onAssembly(new CompletableDisposeOn(this, var1));
   }
}
