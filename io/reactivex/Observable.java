package io.reactivex;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Emitter;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Notification;
import io.reactivex.ObservableConverter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableOperator;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.Observable.1;
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
import io.reactivex.internal.fuseable.ScalarCallable;
import io.reactivex.internal.observers.BlockingFirstObserver;
import io.reactivex.internal.observers.BlockingLastObserver;
import io.reactivex.internal.observers.ForEachWhileObserver;
import io.reactivex.internal.observers.FutureObserver;
import io.reactivex.internal.observers.LambdaObserver;
import io.reactivex.internal.operators.flowable.FlowableFromObservable;
import io.reactivex.internal.operators.flowable.FlowableOnBackpressureError;
import io.reactivex.internal.operators.observable.BlockingObservableIterable;
import io.reactivex.internal.operators.observable.BlockingObservableLatest;
import io.reactivex.internal.operators.observable.BlockingObservableMostRecent;
import io.reactivex.internal.operators.observable.BlockingObservableNext;
import io.reactivex.internal.operators.observable.ObservableAllSingle;
import io.reactivex.internal.operators.observable.ObservableAmb;
import io.reactivex.internal.operators.observable.ObservableAnySingle;
import io.reactivex.internal.operators.observable.ObservableBlockingSubscribe;
import io.reactivex.internal.operators.observable.ObservableBuffer;
import io.reactivex.internal.operators.observable.ObservableBufferBoundary;
import io.reactivex.internal.operators.observable.ObservableBufferBoundarySupplier;
import io.reactivex.internal.operators.observable.ObservableBufferExactBoundary;
import io.reactivex.internal.operators.observable.ObservableBufferTimed;
import io.reactivex.internal.operators.observable.ObservableCache;
import io.reactivex.internal.operators.observable.ObservableCollectSingle;
import io.reactivex.internal.operators.observable.ObservableCombineLatest;
import io.reactivex.internal.operators.observable.ObservableConcatMap;
import io.reactivex.internal.operators.observable.ObservableConcatMapCompletable;
import io.reactivex.internal.operators.observable.ObservableConcatMapEager;
import io.reactivex.internal.operators.observable.ObservableCountSingle;
import io.reactivex.internal.operators.observable.ObservableCreate;
import io.reactivex.internal.operators.observable.ObservableDebounce;
import io.reactivex.internal.operators.observable.ObservableDebounceTimed;
import io.reactivex.internal.operators.observable.ObservableDefer;
import io.reactivex.internal.operators.observable.ObservableDelay;
import io.reactivex.internal.operators.observable.ObservableDelaySubscriptionOther;
import io.reactivex.internal.operators.observable.ObservableDematerialize;
import io.reactivex.internal.operators.observable.ObservableDetach;
import io.reactivex.internal.operators.observable.ObservableDistinct;
import io.reactivex.internal.operators.observable.ObservableDistinctUntilChanged;
import io.reactivex.internal.operators.observable.ObservableDoAfterNext;
import io.reactivex.internal.operators.observable.ObservableDoFinally;
import io.reactivex.internal.operators.observable.ObservableDoOnEach;
import io.reactivex.internal.operators.observable.ObservableDoOnLifecycle;
import io.reactivex.internal.operators.observable.ObservableElementAtMaybe;
import io.reactivex.internal.operators.observable.ObservableElementAtSingle;
import io.reactivex.internal.operators.observable.ObservableEmpty;
import io.reactivex.internal.operators.observable.ObservableError;
import io.reactivex.internal.operators.observable.ObservableFilter;
import io.reactivex.internal.operators.observable.ObservableFlatMap;
import io.reactivex.internal.operators.observable.ObservableFlatMapCompletableCompletable;
import io.reactivex.internal.operators.observable.ObservableFlatMapMaybe;
import io.reactivex.internal.operators.observable.ObservableFlatMapSingle;
import io.reactivex.internal.operators.observable.ObservableFlattenIterable;
import io.reactivex.internal.operators.observable.ObservableFromArray;
import io.reactivex.internal.operators.observable.ObservableFromCallable;
import io.reactivex.internal.operators.observable.ObservableFromFuture;
import io.reactivex.internal.operators.observable.ObservableFromIterable;
import io.reactivex.internal.operators.observable.ObservableFromPublisher;
import io.reactivex.internal.operators.observable.ObservableFromUnsafeSource;
import io.reactivex.internal.operators.observable.ObservableGenerate;
import io.reactivex.internal.operators.observable.ObservableGroupBy;
import io.reactivex.internal.operators.observable.ObservableGroupJoin;
import io.reactivex.internal.operators.observable.ObservableHide;
import io.reactivex.internal.operators.observable.ObservableIgnoreElements;
import io.reactivex.internal.operators.observable.ObservableIgnoreElementsCompletable;
import io.reactivex.internal.operators.observable.ObservableInternalHelper;
import io.reactivex.internal.operators.observable.ObservableInterval;
import io.reactivex.internal.operators.observable.ObservableIntervalRange;
import io.reactivex.internal.operators.observable.ObservableJoin;
import io.reactivex.internal.operators.observable.ObservableJust;
import io.reactivex.internal.operators.observable.ObservableLastMaybe;
import io.reactivex.internal.operators.observable.ObservableLastSingle;
import io.reactivex.internal.operators.observable.ObservableLift;
import io.reactivex.internal.operators.observable.ObservableMap;
import io.reactivex.internal.operators.observable.ObservableMapNotification;
import io.reactivex.internal.operators.observable.ObservableMaterialize;
import io.reactivex.internal.operators.observable.ObservableNever;
import io.reactivex.internal.operators.observable.ObservableObserveOn;
import io.reactivex.internal.operators.observable.ObservableOnErrorNext;
import io.reactivex.internal.operators.observable.ObservableOnErrorReturn;
import io.reactivex.internal.operators.observable.ObservablePublish;
import io.reactivex.internal.operators.observable.ObservablePublishSelector;
import io.reactivex.internal.operators.observable.ObservableRange;
import io.reactivex.internal.operators.observable.ObservableRangeLong;
import io.reactivex.internal.operators.observable.ObservableReduceMaybe;
import io.reactivex.internal.operators.observable.ObservableReduceSeedSingle;
import io.reactivex.internal.operators.observable.ObservableReduceWithSingle;
import io.reactivex.internal.operators.observable.ObservableRepeat;
import io.reactivex.internal.operators.observable.ObservableRepeatUntil;
import io.reactivex.internal.operators.observable.ObservableRepeatWhen;
import io.reactivex.internal.operators.observable.ObservableReplay;
import io.reactivex.internal.operators.observable.ObservableRetryBiPredicate;
import io.reactivex.internal.operators.observable.ObservableRetryPredicate;
import io.reactivex.internal.operators.observable.ObservableRetryWhen;
import io.reactivex.internal.operators.observable.ObservableSampleTimed;
import io.reactivex.internal.operators.observable.ObservableSampleWithObservable;
import io.reactivex.internal.operators.observable.ObservableScalarXMap;
import io.reactivex.internal.operators.observable.ObservableScan;
import io.reactivex.internal.operators.observable.ObservableScanSeed;
import io.reactivex.internal.operators.observable.ObservableSequenceEqualSingle;
import io.reactivex.internal.operators.observable.ObservableSerialized;
import io.reactivex.internal.operators.observable.ObservableSingleMaybe;
import io.reactivex.internal.operators.observable.ObservableSingleSingle;
import io.reactivex.internal.operators.observable.ObservableSkip;
import io.reactivex.internal.operators.observable.ObservableSkipLast;
import io.reactivex.internal.operators.observable.ObservableSkipLastTimed;
import io.reactivex.internal.operators.observable.ObservableSkipUntil;
import io.reactivex.internal.operators.observable.ObservableSkipWhile;
import io.reactivex.internal.operators.observable.ObservableSubscribeOn;
import io.reactivex.internal.operators.observable.ObservableSwitchIfEmpty;
import io.reactivex.internal.operators.observable.ObservableSwitchMap;
import io.reactivex.internal.operators.observable.ObservableTake;
import io.reactivex.internal.operators.observable.ObservableTakeLast;
import io.reactivex.internal.operators.observable.ObservableTakeLastOne;
import io.reactivex.internal.operators.observable.ObservableTakeLastTimed;
import io.reactivex.internal.operators.observable.ObservableTakeUntil;
import io.reactivex.internal.operators.observable.ObservableTakeUntilPredicate;
import io.reactivex.internal.operators.observable.ObservableTakeWhile;
import io.reactivex.internal.operators.observable.ObservableThrottleFirstTimed;
import io.reactivex.internal.operators.observable.ObservableTimeInterval;
import io.reactivex.internal.operators.observable.ObservableTimeout;
import io.reactivex.internal.operators.observable.ObservableTimeoutTimed;
import io.reactivex.internal.operators.observable.ObservableTimer;
import io.reactivex.internal.operators.observable.ObservableToList;
import io.reactivex.internal.operators.observable.ObservableToListSingle;
import io.reactivex.internal.operators.observable.ObservableUnsubscribeOn;
import io.reactivex.internal.operators.observable.ObservableUsing;
import io.reactivex.internal.operators.observable.ObservableWindow;
import io.reactivex.internal.operators.observable.ObservableWindowBoundary;
import io.reactivex.internal.operators.observable.ObservableWindowBoundarySelector;
import io.reactivex.internal.operators.observable.ObservableWindowBoundarySupplier;
import io.reactivex.internal.operators.observable.ObservableWindowTimed;
import io.reactivex.internal.operators.observable.ObservableWithLatestFrom;
import io.reactivex.internal.operators.observable.ObservableWithLatestFromMany;
import io.reactivex.internal.operators.observable.ObservableZip;
import io.reactivex.internal.operators.observable.ObservableZipIterable;
import io.reactivex.internal.util.ArrayListSupplier;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.HashMapSupplier;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.observers.SafeObserver;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;

public abstract class Observable<T extends Object> implements ObservableSource<T> {

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> amb(Iterable<? extends ObservableSource<? extends T>> var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      return RxJavaPlugins.onAssembly(new ObservableAmb((ObservableSource[])null, var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> ambArray(ObservableSource<? extends T> ... var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      int var1 = var0.length;
      return var1 == 0?empty():(var1 == 1?wrap(var0[0]):RxJavaPlugins.onAssembly(new ObservableAmb(var0, (Iterable)null)));
   }

   public static int bufferSize() {
      return Flowable.bufferSize();
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, T5 extends Object, T6 extends Object, T7 extends Object, T8 extends Object, T9 extends Object, R extends Object> Observable<R> combineLatest(ObservableSource<? extends T1> var0, ObservableSource<? extends T2> var1, ObservableSource<? extends T3> var2, ObservableSource<? extends T4> var3, ObservableSource<? extends T5> var4, ObservableSource<? extends T6> var5, ObservableSource<? extends T7> var6, ObservableSource<? extends T8> var7, ObservableSource<? extends T9> var8, Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> var9) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      ObjectHelper.requireNonNull(var4, "source5 is null");
      ObjectHelper.requireNonNull(var5, "source6 is null");
      ObjectHelper.requireNonNull(var6, "source7 is null");
      ObjectHelper.requireNonNull(var7, "source8 is null");
      ObjectHelper.requireNonNull(var8, "source9 is null");
      return combineLatest(Functions.toFunction(var9), bufferSize(), new ObservableSource[]{var0, var1, var2, var3, var4, var5, var6, var7, var8});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, T5 extends Object, T6 extends Object, T7 extends Object, T8 extends Object, R extends Object> Observable<R> combineLatest(ObservableSource<? extends T1> var0, ObservableSource<? extends T2> var1, ObservableSource<? extends T3> var2, ObservableSource<? extends T4> var3, ObservableSource<? extends T5> var4, ObservableSource<? extends T6> var5, ObservableSource<? extends T7> var6, ObservableSource<? extends T8> var7, Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> var8) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      ObjectHelper.requireNonNull(var4, "source5 is null");
      ObjectHelper.requireNonNull(var5, "source6 is null");
      ObjectHelper.requireNonNull(var6, "source7 is null");
      ObjectHelper.requireNonNull(var7, "source8 is null");
      return combineLatest(Functions.toFunction(var8), bufferSize(), new ObservableSource[]{var0, var1, var2, var3, var4, var5, var6, var7});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, T5 extends Object, T6 extends Object, T7 extends Object, R extends Object> Observable<R> combineLatest(ObservableSource<? extends T1> var0, ObservableSource<? extends T2> var1, ObservableSource<? extends T3> var2, ObservableSource<? extends T4> var3, ObservableSource<? extends T5> var4, ObservableSource<? extends T6> var5, ObservableSource<? extends T7> var6, Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> var7) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      ObjectHelper.requireNonNull(var4, "source5 is null");
      ObjectHelper.requireNonNull(var5, "source6 is null");
      ObjectHelper.requireNonNull(var6, "source7 is null");
      return combineLatest(Functions.toFunction(var7), bufferSize(), new ObservableSource[]{var0, var1, var2, var3, var4, var5, var6});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, T5 extends Object, T6 extends Object, R extends Object> Observable<R> combineLatest(ObservableSource<? extends T1> var0, ObservableSource<? extends T2> var1, ObservableSource<? extends T3> var2, ObservableSource<? extends T4> var3, ObservableSource<? extends T5> var4, ObservableSource<? extends T6> var5, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> var6) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      ObjectHelper.requireNonNull(var4, "source5 is null");
      ObjectHelper.requireNonNull(var5, "source6 is null");
      return combineLatest(Functions.toFunction(var6), bufferSize(), new ObservableSource[]{var0, var1, var2, var3, var4, var5});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, T5 extends Object, R extends Object> Observable<R> combineLatest(ObservableSource<? extends T1> var0, ObservableSource<? extends T2> var1, ObservableSource<? extends T3> var2, ObservableSource<? extends T4> var3, ObservableSource<? extends T5> var4, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> var5) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      ObjectHelper.requireNonNull(var4, "source5 is null");
      return combineLatest(Functions.toFunction(var5), bufferSize(), new ObservableSource[]{var0, var1, var2, var3, var4});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, R extends Object> Observable<R> combineLatest(ObservableSource<? extends T1> var0, ObservableSource<? extends T2> var1, ObservableSource<? extends T3> var2, ObservableSource<? extends T4> var3, Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> var4) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      return combineLatest(Functions.toFunction(var4), bufferSize(), new ObservableSource[]{var0, var1, var2, var3});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, R extends Object> Observable<R> combineLatest(ObservableSource<? extends T1> var0, ObservableSource<? extends T2> var1, ObservableSource<? extends T3> var2, Function3<? super T1, ? super T2, ? super T3, ? extends R> var3) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      return combineLatest(Functions.toFunction(var3), bufferSize(), new ObservableSource[]{var0, var1, var2});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, R extends Object> Observable<R> combineLatest(ObservableSource<? extends T1> var0, ObservableSource<? extends T2> var1, BiFunction<? super T1, ? super T2, ? extends R> var2) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      return combineLatest(Functions.toFunction(var2), bufferSize(), new ObservableSource[]{var0, var1});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Observable<R> combineLatest(Function<? super Object[], ? extends R> var0, int var1, ObservableSource<? extends T> ... var2) {
      return combineLatest(var2, var0, var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Observable<R> combineLatest(Iterable<? extends ObservableSource<? extends T>> var0, Function<? super Object[], ? extends R> var1) {
      return combineLatest(var0, var1, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Observable<R> combineLatest(Iterable<? extends ObservableSource<? extends T>> var0, Function<? super Object[], ? extends R> var1, int var2) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      ObjectHelper.requireNonNull(var1, "combiner is null");
      ObjectHelper.verifyPositive(var2, "bufferSize");
      return RxJavaPlugins.onAssembly(new ObservableCombineLatest((ObservableSource[])null, var0, var1, var2 << 1, false));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Observable<R> combineLatest(ObservableSource<? extends T>[] var0, Function<? super Object[], ? extends R> var1) {
      return combineLatest(var0, var1, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Observable<R> combineLatest(ObservableSource<? extends T>[] var0, Function<? super Object[], ? extends R> var1, int var2) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      if(var0.length == 0) {
         return empty();
      } else {
         ObjectHelper.requireNonNull(var1, "combiner is null");
         ObjectHelper.verifyPositive(var2, "bufferSize");
         return RxJavaPlugins.onAssembly(new ObservableCombineLatest(var0, (Iterable)null, var1, var2 << 1, false));
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Observable<R> combineLatestDelayError(Function<? super Object[], ? extends R> var0, int var1, ObservableSource<? extends T> ... var2) {
      return combineLatestDelayError(var2, var0, var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Observable<R> combineLatestDelayError(Iterable<? extends ObservableSource<? extends T>> var0, Function<? super Object[], ? extends R> var1) {
      return combineLatestDelayError(var0, var1, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Observable<R> combineLatestDelayError(Iterable<? extends ObservableSource<? extends T>> var0, Function<? super Object[], ? extends R> var1, int var2) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      ObjectHelper.requireNonNull(var1, "combiner is null");
      ObjectHelper.verifyPositive(var2, "bufferSize");
      return RxJavaPlugins.onAssembly(new ObservableCombineLatest((ObservableSource[])null, var0, var1, var2 << 1, true));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Observable<R> combineLatestDelayError(ObservableSource<? extends T>[] var0, Function<? super Object[], ? extends R> var1) {
      return combineLatestDelayError(var0, var1, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Observable<R> combineLatestDelayError(ObservableSource<? extends T>[] var0, Function<? super Object[], ? extends R> var1, int var2) {
      ObjectHelper.verifyPositive(var2, "bufferSize");
      ObjectHelper.requireNonNull(var1, "combiner is null");
      return var0.length == 0?empty():RxJavaPlugins.onAssembly(new ObservableCombineLatest(var0, (Iterable)null, var1, var2 << 1, true));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> concat(ObservableSource<? extends ObservableSource<? extends T>> var0) {
      return concat(var0, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> concat(ObservableSource<? extends ObservableSource<? extends T>> var0, int var1) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      ObjectHelper.verifyPositive(var1, "prefetch");
      return RxJavaPlugins.onAssembly(new ObservableConcatMap(var0, Functions.identity(), var1, ErrorMode.IMMEDIATE));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> concat(ObservableSource<? extends T> var0, ObservableSource<? extends T> var1) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      return concatArray(new ObservableSource[]{var0, var1});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> concat(ObservableSource<? extends T> var0, ObservableSource<? extends T> var1, ObservableSource<? extends T> var2) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      return concatArray(new ObservableSource[]{var0, var1, var2});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> concat(ObservableSource<? extends T> var0, ObservableSource<? extends T> var1, ObservableSource<? extends T> var2, ObservableSource<? extends T> var3) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      return concatArray(new ObservableSource[]{var0, var1, var2, var3});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> concat(Iterable<? extends ObservableSource<? extends T>> var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      return fromIterable(var0).concatMapDelayError(Functions.identity(), bufferSize(), false);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> concatArray(ObservableSource<? extends T> ... var0) {
      return var0.length == 0?empty():(var0.length == 1?wrap(var0[0]):RxJavaPlugins.onAssembly(new ObservableConcatMap(fromArray(var0), Functions.identity(), bufferSize(), ErrorMode.BOUNDARY)));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> concatArrayDelayError(ObservableSource<? extends T> ... var0) {
      return var0.length == 0?empty():(var0.length == 1?wrap(var0[0]):concatDelayError((ObservableSource)fromArray(var0)));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> concatArrayEager(int var0, int var1, ObservableSource<? extends T> ... var2) {
      return fromArray(var2).concatMapEagerDelayError(Functions.identity(), var0, var1, false);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> concatArrayEager(ObservableSource<? extends T> ... var0) {
      return concatArrayEager(bufferSize(), bufferSize(), var0);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> concatDelayError(ObservableSource<? extends ObservableSource<? extends T>> var0) {
      return concatDelayError(var0, bufferSize(), true);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> concatDelayError(ObservableSource<? extends ObservableSource<? extends T>> var0, int var1, boolean var2) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      ObjectHelper.verifyPositive(var1, "prefetch is null");
      Function var4 = Functions.identity();
      ErrorMode var3;
      if(var2) {
         var3 = ErrorMode.END;
      } else {
         var3 = ErrorMode.BOUNDARY;
      }

      return RxJavaPlugins.onAssembly(new ObservableConcatMap(var0, var4, var1, var3));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> concatDelayError(Iterable<? extends ObservableSource<? extends T>> var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      return concatDelayError((ObservableSource)fromIterable(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> concatEager(ObservableSource<? extends ObservableSource<? extends T>> var0) {
      return concatEager(var0, bufferSize(), bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> concatEager(ObservableSource<? extends ObservableSource<? extends T>> var0, int var1, int var2) {
      ObjectHelper.requireNonNull(Integer.valueOf(var1), "maxConcurrency is null");
      ObjectHelper.requireNonNull(Integer.valueOf(var2), "prefetch is null");
      return wrap(var0).concatMapEager(Functions.identity(), var1, var2);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> concatEager(Iterable<? extends ObservableSource<? extends T>> var0) {
      return concatEager(var0, bufferSize(), bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> concatEager(Iterable<? extends ObservableSource<? extends T>> var0, int var1, int var2) {
      ObjectHelper.requireNonNull(Integer.valueOf(var1), "maxConcurrency is null");
      ObjectHelper.requireNonNull(Integer.valueOf(var2), "prefetch is null");
      return fromIterable(var0).concatMapEagerDelayError(Functions.identity(), var1, var2, false);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> create(ObservableOnSubscribe<T> var0) {
      ObjectHelper.requireNonNull(var0, "source is null");
      return RxJavaPlugins.onAssembly(new ObservableCreate(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> defer(Callable<? extends ObservableSource<? extends T>> var0) {
      ObjectHelper.requireNonNull(var0, "supplier is null");
      return RxJavaPlugins.onAssembly(new ObservableDefer(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   private Observable<T> doOnEach(Consumer<? super T> var1, Consumer<? super Throwable> var2, Action var3, Action var4) {
      ObjectHelper.requireNonNull(var1, "onNext is null");
      ObjectHelper.requireNonNull(var2, "onError is null");
      ObjectHelper.requireNonNull(var3, "onComplete is null");
      ObjectHelper.requireNonNull(var4, "onAfterTerminate is null");
      return RxJavaPlugins.onAssembly(new ObservableDoOnEach(this, var1, var2, var3, var4));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> empty() {
      return RxJavaPlugins.onAssembly(ObservableEmpty.INSTANCE);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> error(Throwable var0) {
      ObjectHelper.requireNonNull(var0, "e is null");
      return error(Functions.justCallable(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> error(Callable<? extends Throwable> var0) {
      ObjectHelper.requireNonNull(var0, "errorSupplier is null");
      return RxJavaPlugins.onAssembly(new ObservableError(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> fromArray(T ... var0) {
      ObjectHelper.requireNonNull(var0, "items is null");
      return var0.length == 0?empty():(var0.length == 1?just(var0[0]):RxJavaPlugins.onAssembly(new ObservableFromArray(var0)));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> fromCallable(Callable<? extends T> var0) {
      ObjectHelper.requireNonNull(var0, "supplier is null");
      return RxJavaPlugins.onAssembly(new ObservableFromCallable(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> fromFuture(Future<? extends T> var0) {
      ObjectHelper.requireNonNull(var0, "future is null");
      return RxJavaPlugins.onAssembly(new ObservableFromFuture(var0, 0L, (TimeUnit)null));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> fromFuture(Future<? extends T> var0, long var1, TimeUnit var3) {
      ObjectHelper.requireNonNull(var0, "future is null");
      ObjectHelper.requireNonNull(var3, "unit is null");
      return RxJavaPlugins.onAssembly(new ObservableFromFuture(var0, var1, var3));
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public static <T extends Object> Observable<T> fromFuture(Future<? extends T> var0, long var1, TimeUnit var3, Scheduler var4) {
      ObjectHelper.requireNonNull(var4, "scheduler is null");
      return fromFuture(var0, var1, var3).subscribeOn(var4);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public static <T extends Object> Observable<T> fromFuture(Future<? extends T> var0, Scheduler var1) {
      ObjectHelper.requireNonNull(var1, "scheduler is null");
      return fromFuture(var0).subscribeOn(var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> fromIterable(Iterable<? extends T> var0) {
      ObjectHelper.requireNonNull(var0, "source is null");
      return RxJavaPlugins.onAssembly(new ObservableFromIterable(var0));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> fromPublisher(Publisher<? extends T> var0) {
      ObjectHelper.requireNonNull(var0, "publisher is null");
      return RxJavaPlugins.onAssembly(new ObservableFromPublisher(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> generate(Consumer<Emitter<T>> var0) {
      ObjectHelper.requireNonNull(var0, "generator  is null");
      return generate(Functions.nullSupplier(), ObservableInternalHelper.simpleGenerator(var0), Functions.emptyConsumer());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, S extends Object> Observable<T> generate(Callable<S> var0, BiConsumer<S, Emitter<T>> var1) {
      ObjectHelper.requireNonNull(var1, "generator  is null");
      return generate(var0, ObservableInternalHelper.simpleBiGenerator(var1), Functions.emptyConsumer());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, S extends Object> Observable<T> generate(Callable<S> var0, BiConsumer<S, Emitter<T>> var1, Consumer<? super S> var2) {
      ObjectHelper.requireNonNull(var1, "generator  is null");
      return generate(var0, ObservableInternalHelper.simpleBiGenerator(var1), var2);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, S extends Object> Observable<T> generate(Callable<S> var0, BiFunction<S, Emitter<T>, S> var1) {
      return generate(var0, var1, Functions.emptyConsumer());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, S extends Object> Observable<T> generate(Callable<S> var0, BiFunction<S, Emitter<T>, S> var1, Consumer<? super S> var2) {
      ObjectHelper.requireNonNull(var0, "initialState is null");
      ObjectHelper.requireNonNull(var1, "generator  is null");
      ObjectHelper.requireNonNull(var2, "disposeState is null");
      return RxJavaPlugins.onAssembly(new ObservableGenerate(var0, var1, var2));
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public static Observable<Long> interval(long var0, long var2, TimeUnit var4) {
      return interval(var0, var2, var4, Schedulers.computation());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public static Observable<Long> interval(long var0, long var2, TimeUnit var4, Scheduler var5) {
      ObjectHelper.requireNonNull(var4, "unit is null");
      ObjectHelper.requireNonNull(var5, "scheduler is null");
      return RxJavaPlugins.onAssembly(new ObservableInterval(Math.max(0L, var0), Math.max(0L, var2), var4, var5));
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public static Observable<Long> interval(long var0, TimeUnit var2) {
      return interval(var0, var0, var2, Schedulers.computation());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public static Observable<Long> interval(long var0, TimeUnit var2, Scheduler var3) {
      return interval(var0, var0, var2, var3);
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public static Observable<Long> intervalRange(long var0, long var2, long var4, long var6, TimeUnit var8) {
      return intervalRange(var0, var2, var4, var6, var8, Schedulers.computation());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public static Observable<Long> intervalRange(long var0, long var2, long var4, long var6, TimeUnit var8, Scheduler var9) {
      if(var2 < 0L) {
         StringBuilder var10 = new StringBuilder();
         var10.append("count >= 0 required but it was ");
         var10.append(var2);
         throw new IllegalArgumentException(var10.toString());
      } else if(var2 == 0L) {
         return empty().delay(var4, var8, var9);
      } else {
         var2 = var0 + (var2 - 1L);
         if(var0 > 0L && var2 < 0L) {
            throw new IllegalArgumentException("Overflow! start + count is bigger than Long.MAX_VALUE");
         } else {
            ObjectHelper.requireNonNull(var8, "unit is null");
            ObjectHelper.requireNonNull(var9, "scheduler is null");
            return RxJavaPlugins.onAssembly(new ObservableIntervalRange(var0, var2, Math.max(0L, var4), Math.max(0L, var6), var8, var9));
         }
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> just(T var0) {
      ObjectHelper.requireNonNull(var0, "The item is null");
      return RxJavaPlugins.onAssembly(new ObservableJust(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> just(T var0, T var1) {
      ObjectHelper.requireNonNull(var0, "The first item is null");
      ObjectHelper.requireNonNull(var1, "The second item is null");
      return fromArray(new Object[]{var0, var1});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> just(T var0, T var1, T var2) {
      ObjectHelper.requireNonNull(var0, "The first item is null");
      ObjectHelper.requireNonNull(var1, "The second item is null");
      ObjectHelper.requireNonNull(var2, "The third item is null");
      return fromArray(new Object[]{var0, var1, var2});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> just(T var0, T var1, T var2, T var3) {
      ObjectHelper.requireNonNull(var0, "The first item is null");
      ObjectHelper.requireNonNull(var1, "The second item is null");
      ObjectHelper.requireNonNull(var2, "The third item is null");
      ObjectHelper.requireNonNull(var3, "The fourth item is null");
      return fromArray(new Object[]{var0, var1, var2, var3});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> just(T var0, T var1, T var2, T var3, T var4) {
      ObjectHelper.requireNonNull(var0, "The first item is null");
      ObjectHelper.requireNonNull(var1, "The second item is null");
      ObjectHelper.requireNonNull(var2, "The third item is null");
      ObjectHelper.requireNonNull(var3, "The fourth item is null");
      ObjectHelper.requireNonNull(var4, "The fifth item is null");
      return fromArray(new Object[]{var0, var1, var2, var3, var4});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> just(T var0, T var1, T var2, T var3, T var4, T var5) {
      ObjectHelper.requireNonNull(var0, "The first item is null");
      ObjectHelper.requireNonNull(var1, "The second item is null");
      ObjectHelper.requireNonNull(var2, "The third item is null");
      ObjectHelper.requireNonNull(var3, "The fourth item is null");
      ObjectHelper.requireNonNull(var4, "The fifth item is null");
      ObjectHelper.requireNonNull(var5, "The sixth item is null");
      return fromArray(new Object[]{var0, var1, var2, var3, var4, var5});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> just(T var0, T var1, T var2, T var3, T var4, T var5, T var6) {
      ObjectHelper.requireNonNull(var0, "The first item is null");
      ObjectHelper.requireNonNull(var1, "The second item is null");
      ObjectHelper.requireNonNull(var2, "The third item is null");
      ObjectHelper.requireNonNull(var3, "The fourth item is null");
      ObjectHelper.requireNonNull(var4, "The fifth item is null");
      ObjectHelper.requireNonNull(var5, "The sixth item is null");
      ObjectHelper.requireNonNull(var6, "The seventh item is null");
      return fromArray(new Object[]{var0, var1, var2, var3, var4, var5, var6});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> just(T var0, T var1, T var2, T var3, T var4, T var5, T var6, T var7) {
      ObjectHelper.requireNonNull(var0, "The first item is null");
      ObjectHelper.requireNonNull(var1, "The second item is null");
      ObjectHelper.requireNonNull(var2, "The third item is null");
      ObjectHelper.requireNonNull(var3, "The fourth item is null");
      ObjectHelper.requireNonNull(var4, "The fifth item is null");
      ObjectHelper.requireNonNull(var5, "The sixth item is null");
      ObjectHelper.requireNonNull(var6, "The seventh item is null");
      ObjectHelper.requireNonNull(var7, "The eighth item is null");
      return fromArray(new Object[]{var0, var1, var2, var3, var4, var5, var6, var7});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> just(T var0, T var1, T var2, T var3, T var4, T var5, T var6, T var7, T var8) {
      ObjectHelper.requireNonNull(var0, "The first item is null");
      ObjectHelper.requireNonNull(var1, "The second item is null");
      ObjectHelper.requireNonNull(var2, "The third item is null");
      ObjectHelper.requireNonNull(var3, "The fourth item is null");
      ObjectHelper.requireNonNull(var4, "The fifth item is null");
      ObjectHelper.requireNonNull(var5, "The sixth item is null");
      ObjectHelper.requireNonNull(var6, "The seventh item is null");
      ObjectHelper.requireNonNull(var7, "The eighth item is null");
      ObjectHelper.requireNonNull(var8, "The ninth item is null");
      return fromArray(new Object[]{var0, var1, var2, var3, var4, var5, var6, var7, var8});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> just(T var0, T var1, T var2, T var3, T var4, T var5, T var6, T var7, T var8, T var9) {
      ObjectHelper.requireNonNull(var0, "The first item is null");
      ObjectHelper.requireNonNull(var1, "The second item is null");
      ObjectHelper.requireNonNull(var2, "The third item is null");
      ObjectHelper.requireNonNull(var3, "The fourth item is null");
      ObjectHelper.requireNonNull(var4, "The fifth item is null");
      ObjectHelper.requireNonNull(var5, "The sixth item is null");
      ObjectHelper.requireNonNull(var6, "The seventh item is null");
      ObjectHelper.requireNonNull(var7, "The eighth item is null");
      ObjectHelper.requireNonNull(var8, "The ninth item is null");
      ObjectHelper.requireNonNull(var9, "The tenth item is null");
      return fromArray(new Object[]{var0, var1, var2, var3, var4, var5, var6, var7, var8, var9});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> merge(ObservableSource<? extends ObservableSource<? extends T>> var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      return RxJavaPlugins.onAssembly(new ObservableFlatMap(var0, Functions.identity(), false, Integer.MAX_VALUE, bufferSize()));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> merge(ObservableSource<? extends ObservableSource<? extends T>> var0, int var1) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      ObjectHelper.verifyPositive(var1, "maxConcurrency");
      return RxJavaPlugins.onAssembly(new ObservableFlatMap(var0, Functions.identity(), false, var1, bufferSize()));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> merge(ObservableSource<? extends T> var0, ObservableSource<? extends T> var1) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      return fromArray(new ObservableSource[]{var0, var1}).flatMap(Functions.identity(), false, 2);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> merge(ObservableSource<? extends T> var0, ObservableSource<? extends T> var1, ObservableSource<? extends T> var2) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      return fromArray(new ObservableSource[]{var0, var1, var2}).flatMap(Functions.identity(), false, 3);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> merge(ObservableSource<? extends T> var0, ObservableSource<? extends T> var1, ObservableSource<? extends T> var2, ObservableSource<? extends T> var3) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      return fromArray(new ObservableSource[]{var0, var1, var2, var3}).flatMap(Functions.identity(), false, 4);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> merge(Iterable<? extends ObservableSource<? extends T>> var0) {
      return fromIterable(var0).flatMap(Functions.identity());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> merge(Iterable<? extends ObservableSource<? extends T>> var0, int var1) {
      return fromIterable(var0).flatMap(Functions.identity(), var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> merge(Iterable<? extends ObservableSource<? extends T>> var0, int var1, int var2) {
      return fromIterable(var0).flatMap(Functions.identity(), false, var1, var2);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> mergeArray(int var0, int var1, ObservableSource<? extends T> ... var2) {
      return fromArray(var2).flatMap(Functions.identity(), false, var0, var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> mergeArray(ObservableSource<? extends T> ... var0) {
      return fromArray(var0).flatMap(Functions.identity(), var0.length);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> mergeArrayDelayError(int var0, int var1, ObservableSource<? extends T> ... var2) {
      return fromArray(var2).flatMap(Functions.identity(), true, var0, var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> mergeArrayDelayError(ObservableSource<? extends T> ... var0) {
      return fromArray(var0).flatMap(Functions.identity(), true, var0.length);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> mergeDelayError(ObservableSource<? extends ObservableSource<? extends T>> var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      return RxJavaPlugins.onAssembly(new ObservableFlatMap(var0, Functions.identity(), true, Integer.MAX_VALUE, bufferSize()));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> mergeDelayError(ObservableSource<? extends ObservableSource<? extends T>> var0, int var1) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      ObjectHelper.verifyPositive(var1, "maxConcurrency");
      return RxJavaPlugins.onAssembly(new ObservableFlatMap(var0, Functions.identity(), true, var1, bufferSize()));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> mergeDelayError(ObservableSource<? extends T> var0, ObservableSource<? extends T> var1) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      return fromArray(new ObservableSource[]{var0, var1}).flatMap(Functions.identity(), true, 2);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> mergeDelayError(ObservableSource<? extends T> var0, ObservableSource<? extends T> var1, ObservableSource<? extends T> var2) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      return fromArray(new ObservableSource[]{var0, var1, var2}).flatMap(Functions.identity(), true, 3);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> mergeDelayError(ObservableSource<? extends T> var0, ObservableSource<? extends T> var1, ObservableSource<? extends T> var2, ObservableSource<? extends T> var3) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      return fromArray(new ObservableSource[]{var0, var1, var2, var3}).flatMap(Functions.identity(), true, 4);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> mergeDelayError(Iterable<? extends ObservableSource<? extends T>> var0) {
      return fromIterable(var0).flatMap(Functions.identity(), true);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> mergeDelayError(Iterable<? extends ObservableSource<? extends T>> var0, int var1) {
      return fromIterable(var0).flatMap(Functions.identity(), true, var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> mergeDelayError(Iterable<? extends ObservableSource<? extends T>> var0, int var1, int var2) {
      return fromIterable(var0).flatMap(Functions.identity(), true, var1, var2);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> never() {
      return RxJavaPlugins.onAssembly(ObservableNever.INSTANCE);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Observable<Integer> range(int var0, int var1) {
      if(var1 < 0) {
         StringBuilder var2 = new StringBuilder();
         var2.append("count >= 0 required but it was ");
         var2.append(var1);
         throw new IllegalArgumentException(var2.toString());
      } else if(var1 == 0) {
         return empty();
      } else if(var1 == 1) {
         return just(Integer.valueOf(var0));
      } else if((long)var0 + (long)(var1 - 1) > 2147483647L) {
         throw new IllegalArgumentException("Integer overflow");
      } else {
         return RxJavaPlugins.onAssembly(new ObservableRange(var0, var1));
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Observable<Long> rangeLong(long var0, long var2) {
      if(var2 < 0L) {
         StringBuilder var4 = new StringBuilder();
         var4.append("count >= 0 required but it was ");
         var4.append(var2);
         throw new IllegalArgumentException(var4.toString());
      } else if(var2 == 0L) {
         return empty();
      } else if(var2 == 1L) {
         return just(Long.valueOf(var0));
      } else if(var0 > 0L && var0 + (var2 - 1L) < 0L) {
         throw new IllegalArgumentException("Overflow! start + count is bigger than Long.MAX_VALUE");
      } else {
         return RxJavaPlugins.onAssembly(new ObservableRangeLong(var0, var2));
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Single<Boolean> sequenceEqual(ObservableSource<? extends T> var0, ObservableSource<? extends T> var1) {
      return sequenceEqual(var0, var1, ObjectHelper.equalsPredicate(), bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Single<Boolean> sequenceEqual(ObservableSource<? extends T> var0, ObservableSource<? extends T> var1, int var2) {
      return sequenceEqual(var0, var1, ObjectHelper.equalsPredicate(), var2);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Single<Boolean> sequenceEqual(ObservableSource<? extends T> var0, ObservableSource<? extends T> var1, BiPredicate<? super T, ? super T> var2) {
      return sequenceEqual(var0, var1, var2, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Single<Boolean> sequenceEqual(ObservableSource<? extends T> var0, ObservableSource<? extends T> var1, BiPredicate<? super T, ? super T> var2, int var3) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "isEqual is null");
      ObjectHelper.verifyPositive(var3, "bufferSize");
      return RxJavaPlugins.onAssembly(new ObservableSequenceEqualSingle(var0, var1, var2, var3));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> switchOnNext(ObservableSource<? extends ObservableSource<? extends T>> var0) {
      return switchOnNext(var0, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> switchOnNext(ObservableSource<? extends ObservableSource<? extends T>> var0, int var1) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      ObjectHelper.verifyPositive(var1, "bufferSize");
      return RxJavaPlugins.onAssembly(new ObservableSwitchMap(var0, Functions.identity(), var1, false));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> switchOnNextDelayError(ObservableSource<? extends ObservableSource<? extends T>> var0) {
      return switchOnNextDelayError(var0, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> switchOnNextDelayError(ObservableSource<? extends ObservableSource<? extends T>> var0, int var1) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      ObjectHelper.verifyPositive(var1, "prefetch");
      return RxJavaPlugins.onAssembly(new ObservableSwitchMap(var0, Functions.identity(), var1, true));
   }

   private Observable<T> timeout0(long var1, TimeUnit var3, ObservableSource<? extends T> var4, Scheduler var5) {
      ObjectHelper.requireNonNull(var3, "timeUnit is null");
      ObjectHelper.requireNonNull(var5, "scheduler is null");
      return RxJavaPlugins.onAssembly(new ObservableTimeoutTimed(this, var1, var3, var5, var4));
   }

   private <U extends Object, V extends Object> Observable<T> timeout0(ObservableSource<U> var1, Function<? super T, ? extends ObservableSource<V>> var2, ObservableSource<? extends T> var3) {
      ObjectHelper.requireNonNull(var2, "itemTimeoutIndicator is null");
      return RxJavaPlugins.onAssembly(new ObservableTimeout(this, var1, var2, var3));
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public static Observable<Long> timer(long var0, TimeUnit var2) {
      return timer(var0, var2, Schedulers.computation());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public static Observable<Long> timer(long var0, TimeUnit var2, Scheduler var3) {
      ObjectHelper.requireNonNull(var2, "unit is null");
      ObjectHelper.requireNonNull(var3, "scheduler is null");
      return RxJavaPlugins.onAssembly(new ObservableTimer(Math.max(var0, 0L), var2, var3));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> unsafeCreate(ObservableSource<T> var0) {
      ObjectHelper.requireNonNull(var0, "source is null");
      ObjectHelper.requireNonNull(var0, "onSubscribe is null");
      if(var0 instanceof Observable) {
         throw new IllegalArgumentException("unsafeCreate(Observable) should be upgraded");
      } else {
         return RxJavaPlugins.onAssembly(new ObservableFromUnsafeSource(var0));
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, D extends Object> Observable<T> using(Callable<? extends D> var0, Function<? super D, ? extends ObservableSource<? extends T>> var1, Consumer<? super D> var2) {
      return using(var0, var1, var2, true);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, D extends Object> Observable<T> using(Callable<? extends D> var0, Function<? super D, ? extends ObservableSource<? extends T>> var1, Consumer<? super D> var2, boolean var3) {
      ObjectHelper.requireNonNull(var0, "resourceSupplier is null");
      ObjectHelper.requireNonNull(var1, "sourceSupplier is null");
      ObjectHelper.requireNonNull(var2, "disposer is null");
      return RxJavaPlugins.onAssembly(new ObservableUsing(var0, var1, var2, var3));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Observable<T> wrap(ObservableSource<T> var0) {
      ObjectHelper.requireNonNull(var0, "source is null");
      return var0 instanceof Observable?RxJavaPlugins.onAssembly((Observable)var0):RxJavaPlugins.onAssembly(new ObservableFromUnsafeSource(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, T5 extends Object, T6 extends Object, T7 extends Object, T8 extends Object, T9 extends Object, R extends Object> Observable<R> zip(ObservableSource<? extends T1> var0, ObservableSource<? extends T2> var1, ObservableSource<? extends T3> var2, ObservableSource<? extends T4> var3, ObservableSource<? extends T5> var4, ObservableSource<? extends T6> var5, ObservableSource<? extends T7> var6, ObservableSource<? extends T8> var7, ObservableSource<? extends T9> var8, Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> var9) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      ObjectHelper.requireNonNull(var4, "source5 is null");
      ObjectHelper.requireNonNull(var5, "source6 is null");
      ObjectHelper.requireNonNull(var6, "source7 is null");
      ObjectHelper.requireNonNull(var7, "source8 is null");
      ObjectHelper.requireNonNull(var8, "source9 is null");
      return zipArray(Functions.toFunction(var9), false, bufferSize(), new ObservableSource[]{var0, var1, var2, var3, var4, var5, var6, var7, var8});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, T5 extends Object, T6 extends Object, T7 extends Object, T8 extends Object, R extends Object> Observable<R> zip(ObservableSource<? extends T1> var0, ObservableSource<? extends T2> var1, ObservableSource<? extends T3> var2, ObservableSource<? extends T4> var3, ObservableSource<? extends T5> var4, ObservableSource<? extends T6> var5, ObservableSource<? extends T7> var6, ObservableSource<? extends T8> var7, Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> var8) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      ObjectHelper.requireNonNull(var4, "source5 is null");
      ObjectHelper.requireNonNull(var5, "source6 is null");
      ObjectHelper.requireNonNull(var6, "source7 is null");
      ObjectHelper.requireNonNull(var7, "source8 is null");
      return zipArray(Functions.toFunction(var8), false, bufferSize(), new ObservableSource[]{var0, var1, var2, var3, var4, var5, var6, var7});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, T5 extends Object, T6 extends Object, T7 extends Object, R extends Object> Observable<R> zip(ObservableSource<? extends T1> var0, ObservableSource<? extends T2> var1, ObservableSource<? extends T3> var2, ObservableSource<? extends T4> var3, ObservableSource<? extends T5> var4, ObservableSource<? extends T6> var5, ObservableSource<? extends T7> var6, Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> var7) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      ObjectHelper.requireNonNull(var4, "source5 is null");
      ObjectHelper.requireNonNull(var5, "source6 is null");
      ObjectHelper.requireNonNull(var6, "source7 is null");
      return zipArray(Functions.toFunction(var7), false, bufferSize(), new ObservableSource[]{var0, var1, var2, var3, var4, var5, var6});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, T5 extends Object, T6 extends Object, R extends Object> Observable<R> zip(ObservableSource<? extends T1> var0, ObservableSource<? extends T2> var1, ObservableSource<? extends T3> var2, ObservableSource<? extends T4> var3, ObservableSource<? extends T5> var4, ObservableSource<? extends T6> var5, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> var6) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      ObjectHelper.requireNonNull(var4, "source5 is null");
      ObjectHelper.requireNonNull(var5, "source6 is null");
      return zipArray(Functions.toFunction(var6), false, bufferSize(), new ObservableSource[]{var0, var1, var2, var3, var4, var5});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, T5 extends Object, R extends Object> Observable<R> zip(ObservableSource<? extends T1> var0, ObservableSource<? extends T2> var1, ObservableSource<? extends T3> var2, ObservableSource<? extends T4> var3, ObservableSource<? extends T5> var4, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> var5) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      ObjectHelper.requireNonNull(var4, "source5 is null");
      return zipArray(Functions.toFunction(var5), false, bufferSize(), new ObservableSource[]{var0, var1, var2, var3, var4});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, R extends Object> Observable<R> zip(ObservableSource<? extends T1> var0, ObservableSource<? extends T2> var1, ObservableSource<? extends T3> var2, ObservableSource<? extends T4> var3, Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> var4) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      return zipArray(Functions.toFunction(var4), false, bufferSize(), new ObservableSource[]{var0, var1, var2, var3});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, R extends Object> Observable<R> zip(ObservableSource<? extends T1> var0, ObservableSource<? extends T2> var1, ObservableSource<? extends T3> var2, Function3<? super T1, ? super T2, ? super T3, ? extends R> var3) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      return zipArray(Functions.toFunction(var3), false, bufferSize(), new ObservableSource[]{var0, var1, var2});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, R extends Object> Observable<R> zip(ObservableSource<? extends T1> var0, ObservableSource<? extends T2> var1, BiFunction<? super T1, ? super T2, ? extends R> var2) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      return zipArray(Functions.toFunction(var2), false, bufferSize(), new ObservableSource[]{var0, var1});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, R extends Object> Observable<R> zip(ObservableSource<? extends T1> var0, ObservableSource<? extends T2> var1, BiFunction<? super T1, ? super T2, ? extends R> var2, boolean var3) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      return zipArray(Functions.toFunction(var2), var3, bufferSize(), new ObservableSource[]{var0, var1});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, R extends Object> Observable<R> zip(ObservableSource<? extends T1> var0, ObservableSource<? extends T2> var1, BiFunction<? super T1, ? super T2, ? extends R> var2, boolean var3, int var4) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      return zipArray(Functions.toFunction(var2), var3, var4, new ObservableSource[]{var0, var1});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Observable<R> zip(ObservableSource<? extends ObservableSource<? extends T>> var0, Function<? super Object[], ? extends R> var1) {
      ObjectHelper.requireNonNull(var1, "zipper is null");
      ObjectHelper.requireNonNull(var0, "sources is null");
      return RxJavaPlugins.onAssembly((new ObservableToList(var0, 16)).flatMap(ObservableInternalHelper.zipIterable(var1)));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Observable<R> zip(Iterable<? extends ObservableSource<? extends T>> var0, Function<? super Object[], ? extends R> var1) {
      ObjectHelper.requireNonNull(var1, "zipper is null");
      ObjectHelper.requireNonNull(var0, "sources is null");
      return RxJavaPlugins.onAssembly(new ObservableZip((ObservableSource[])null, var0, var1, bufferSize(), false));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Observable<R> zipArray(Function<? super Object[], ? extends R> var0, boolean var1, int var2, ObservableSource<? extends T> ... var3) {
      if(var3.length == 0) {
         return empty();
      } else {
         ObjectHelper.requireNonNull(var0, "zipper is null");
         ObjectHelper.verifyPositive(var2, "bufferSize");
         return RxJavaPlugins.onAssembly(new ObservableZip(var3, (Iterable)null, var0, var2, var1));
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Observable<R> zipIterable(Iterable<? extends ObservableSource<? extends T>> var0, Function<? super Object[], ? extends R> var1, boolean var2, int var3) {
      ObjectHelper.requireNonNull(var1, "zipper is null");
      ObjectHelper.requireNonNull(var0, "sources is null");
      ObjectHelper.verifyPositive(var3, "bufferSize");
      return RxJavaPlugins.onAssembly(new ObservableZip((ObservableSource[])null, var0, var1, var3, var2));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<Boolean> all(Predicate<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "predicate is null");
      return RxJavaPlugins.onAssembly(new ObservableAllSingle(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> ambWith(ObservableSource<? extends T> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return ambArray(new ObservableSource[]{this, var1});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<Boolean> any(Predicate<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "predicate is null");
      return RxJavaPlugins.onAssembly(new ObservableAnySingle(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   @Experimental
   public final <R extends Object> R as(@NonNull ObservableConverter<T, ? extends R> var1) {
      return ((ObservableConverter)ObjectHelper.requireNonNull(var1, "converter is null")).apply(this);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final T blockingFirst() {
      BlockingFirstObserver var1 = new BlockingFirstObserver();
      this.subscribe((Observer)var1);
      Object var2 = var1.blockingGet();
      if(var2 != null) {
         return var2;
      } else {
         throw new NoSuchElementException();
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final T blockingFirst(T var1) {
      BlockingFirstObserver var2 = new BlockingFirstObserver();
      this.subscribe((Observer)var2);
      Object var3 = var2.blockingGet();
      if(var3 != null) {
         var1 = var3;
      }

      return var1;
   }

   @SchedulerSupport("none")
   public final void blockingForEach(Consumer<? super T> var1) {
      Iterator var2 = this.blockingIterable().iterator();

      while(var2.hasNext()) {
         try {
            var1.accept(var2.next());
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            ((Disposable)var2).dispose();
            throw ExceptionHelper.wrapOrThrow(var3);
         }
      }

   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Iterable<T> blockingIterable() {
      return this.blockingIterable(bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Iterable<T> blockingIterable(int var1) {
      ObjectHelper.verifyPositive(var1, "bufferSize");
      return new BlockingObservableIterable(this, var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final T blockingLast() {
      BlockingLastObserver var1 = new BlockingLastObserver();
      this.subscribe((Observer)var1);
      Object var2 = var1.blockingGet();
      if(var2 != null) {
         return var2;
      } else {
         throw new NoSuchElementException();
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final T blockingLast(T var1) {
      BlockingLastObserver var2 = new BlockingLastObserver();
      this.subscribe((Observer)var2);
      Object var3 = var2.blockingGet();
      if(var3 != null) {
         var1 = var3;
      }

      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Iterable<T> blockingLatest() {
      return new BlockingObservableLatest(this);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Iterable<T> blockingMostRecent(T var1) {
      return new BlockingObservableMostRecent(this, var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Iterable<T> blockingNext() {
      return new BlockingObservableNext(this);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final T blockingSingle() {
      Object var1 = this.singleElement().blockingGet();
      if(var1 == null) {
         throw new NoSuchElementException();
      } else {
         return var1;
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final T blockingSingle(T var1) {
      return this.single(var1).blockingGet();
   }

   @SchedulerSupport("none")
   public final void blockingSubscribe() {
      ObservableBlockingSubscribe.subscribe(this);
   }

   @SchedulerSupport("none")
   public final void blockingSubscribe(Observer<? super T> var1) {
      ObservableBlockingSubscribe.subscribe(this, var1);
   }

   @SchedulerSupport("none")
   public final void blockingSubscribe(Consumer<? super T> var1) {
      ObservableBlockingSubscribe.subscribe(this, var1, Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION);
   }

   @SchedulerSupport("none")
   public final void blockingSubscribe(Consumer<? super T> var1, Consumer<? super Throwable> var2) {
      ObservableBlockingSubscribe.subscribe(this, var1, var2, Functions.EMPTY_ACTION);
   }

   @SchedulerSupport("none")
   public final void blockingSubscribe(Consumer<? super T> var1, Consumer<? super Throwable> var2, Action var3) {
      ObservableBlockingSubscribe.subscribe(this, var1, var2, var3);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<List<T>> buffer(int var1) {
      return this.buffer(var1, var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<List<T>> buffer(int var1, int var2) {
      return this.buffer(var1, var2, ArrayListSupplier.asCallable());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object & Collection<? super T>> Observable<U> buffer(int var1, int var2, Callable<U> var3) {
      ObjectHelper.verifyPositive(var1, "count");
      ObjectHelper.verifyPositive(var2, "skip");
      ObjectHelper.requireNonNull(var3, "bufferSupplier is null");
      return RxJavaPlugins.onAssembly(new ObservableBuffer(this, var1, var2, var3));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object & Collection<? super T>> Observable<U> buffer(int var1, Callable<U> var2) {
      return this.buffer(var1, var1, var2);
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Observable<List<T>> buffer(long var1, long var3, TimeUnit var5) {
      return this.buffer(var1, var3, var5, Schedulers.computation(), ArrayListSupplier.asCallable());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<List<T>> buffer(long var1, long var3, TimeUnit var5, Scheduler var6) {
      return this.buffer(var1, var3, var5, var6, ArrayListSupplier.asCallable());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final <U extends Object & Collection<? super T>> Observable<U> buffer(long var1, long var3, TimeUnit var5, Scheduler var6, Callable<U> var7) {
      ObjectHelper.requireNonNull(var5, "unit is null");
      ObjectHelper.requireNonNull(var6, "scheduler is null");
      ObjectHelper.requireNonNull(var7, "bufferSupplier is null");
      return RxJavaPlugins.onAssembly(new ObservableBufferTimed(this, var1, var3, var5, var6, var7, Integer.MAX_VALUE, false));
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Observable<List<T>> buffer(long var1, TimeUnit var3) {
      return this.buffer(var1, var3, Schedulers.computation(), Integer.MAX_VALUE);
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Observable<List<T>> buffer(long var1, TimeUnit var3, int var4) {
      return this.buffer(var1, var3, Schedulers.computation(), var4);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<List<T>> buffer(long var1, TimeUnit var3, Scheduler var4) {
      return this.buffer(var1, var3, var4, Integer.MAX_VALUE, ArrayListSupplier.asCallable(), false);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<List<T>> buffer(long var1, TimeUnit var3, Scheduler var4, int var5) {
      return this.buffer(var1, var3, var4, var5, ArrayListSupplier.asCallable(), false);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final <U extends Object & Collection<? super T>> Observable<U> buffer(long var1, TimeUnit var3, Scheduler var4, int var5, Callable<U> var6, boolean var7) {
      ObjectHelper.requireNonNull(var3, "unit is null");
      ObjectHelper.requireNonNull(var4, "scheduler is null");
      ObjectHelper.requireNonNull(var6, "bufferSupplier is null");
      ObjectHelper.verifyPositive(var5, "count");
      return RxJavaPlugins.onAssembly(new ObservableBufferTimed(this, var1, var1, var3, var4, var6, var5, var7));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <B extends Object> Observable<List<T>> buffer(ObservableSource<B> var1) {
      return this.buffer(var1, ArrayListSupplier.asCallable());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <B extends Object> Observable<List<T>> buffer(ObservableSource<B> var1, int var2) {
      ObjectHelper.verifyPositive(var2, "initialCapacity");
      return this.buffer(var1, Functions.createArrayList(var2));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <TOpening extends Object, TClosing extends Object> Observable<List<T>> buffer(ObservableSource<? extends TOpening> var1, Function<? super TOpening, ? extends ObservableSource<? extends TClosing>> var2) {
      return this.buffer(var1, var2, ArrayListSupplier.asCallable());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <TOpening extends Object, TClosing extends Object, U extends Object & Collection<? super T>> Observable<U> buffer(ObservableSource<? extends TOpening> var1, Function<? super TOpening, ? extends ObservableSource<? extends TClosing>> var2, Callable<U> var3) {
      ObjectHelper.requireNonNull(var1, "openingIndicator is null");
      ObjectHelper.requireNonNull(var2, "closingIndicator is null");
      ObjectHelper.requireNonNull(var3, "bufferSupplier is null");
      return RxJavaPlugins.onAssembly(new ObservableBufferBoundary(this, var1, var2, var3));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <B extends Object, U extends Object & Collection<? super T>> Observable<U> buffer(ObservableSource<B> var1, Callable<U> var2) {
      ObjectHelper.requireNonNull(var1, "boundary is null");
      ObjectHelper.requireNonNull(var2, "bufferSupplier is null");
      return RxJavaPlugins.onAssembly(new ObservableBufferExactBoundary(this, var1, var2));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <B extends Object> Observable<List<T>> buffer(Callable<? extends ObservableSource<B>> var1) {
      return this.buffer(var1, ArrayListSupplier.asCallable());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <B extends Object, U extends Object & Collection<? super T>> Observable<U> buffer(Callable<? extends ObservableSource<B>> var1, Callable<U> var2) {
      ObjectHelper.requireNonNull(var1, "boundarySupplier is null");
      ObjectHelper.requireNonNull(var2, "bufferSupplier is null");
      return RxJavaPlugins.onAssembly(new ObservableBufferBoundarySupplier(this, var1, var2));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> cache() {
      return ObservableCache.from(this);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> cacheWithInitialCapacity(int var1) {
      return ObservableCache.from(this, var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Observable<U> cast(Class<U> var1) {
      ObjectHelper.requireNonNull(var1, "clazz is null");
      return this.map(Functions.castFunction(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Single<U> collect(Callable<? extends U> var1, BiConsumer<? super U, ? super T> var2) {
      ObjectHelper.requireNonNull(var1, "initialValueSupplier is null");
      ObjectHelper.requireNonNull(var2, "collector is null");
      return RxJavaPlugins.onAssembly(new ObservableCollectSingle(this, var1, var2));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Single<U> collectInto(U var1, BiConsumer<? super U, ? super T> var2) {
      ObjectHelper.requireNonNull(var1, "initialValue is null");
      return this.collect(Functions.justCallable(var1), var2);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> compose(ObservableTransformer<? super T, ? extends R> var1) {
      return wrap(((ObservableTransformer)ObjectHelper.requireNonNull(var1, "composer is null")).apply(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> concatMap(Function<? super T, ? extends ObservableSource<? extends R>> var1) {
      return this.concatMap(var1, 2);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> concatMap(Function<? super T, ? extends ObservableSource<? extends R>> var1, int var2) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.verifyPositive(var2, "prefetch");
      if(this instanceof ScalarCallable) {
         Object var3 = ((ScalarCallable)this).call();
         return var3 == null?empty():ObservableScalarXMap.scalarXMap(var3, var1);
      } else {
         return RxJavaPlugins.onAssembly(new ObservableConcatMap(this, var1, var2, ErrorMode.IMMEDIATE));
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   @Experimental
   public final Completable concatMapCompletable(Function<? super T, ? extends CompletableSource> var1) {
      return this.concatMapCompletable(var1, 2);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   @Experimental
   public final Completable concatMapCompletable(Function<? super T, ? extends CompletableSource> var1, int var2) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.verifyPositive(var2, "capacityHint");
      return RxJavaPlugins.onAssembly(new ObservableConcatMapCompletable(this, var1, var2));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> concatMapDelayError(Function<? super T, ? extends ObservableSource<? extends R>> var1) {
      return this.concatMapDelayError(var1, bufferSize(), true);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> concatMapDelayError(Function<? super T, ? extends ObservableSource<? extends R>> var1, int var2, boolean var3) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.verifyPositive(var2, "prefetch");
      if(this instanceof ScalarCallable) {
         Object var5 = ((ScalarCallable)this).call();
         return var5 == null?empty():ObservableScalarXMap.scalarXMap(var5, var1);
      } else {
         ErrorMode var4;
         if(var3) {
            var4 = ErrorMode.END;
         } else {
            var4 = ErrorMode.BOUNDARY;
         }

         return RxJavaPlugins.onAssembly(new ObservableConcatMap(this, var1, var2, var4));
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> concatMapEager(Function<? super T, ? extends ObservableSource<? extends R>> var1) {
      return this.concatMapEager(var1, Integer.MAX_VALUE, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> concatMapEager(Function<? super T, ? extends ObservableSource<? extends R>> var1, int var2, int var3) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.verifyPositive(var2, "maxConcurrency");
      ObjectHelper.verifyPositive(var3, "prefetch");
      return RxJavaPlugins.onAssembly(new ObservableConcatMapEager(this, var1, ErrorMode.IMMEDIATE, var2, var3));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> concatMapEagerDelayError(Function<? super T, ? extends ObservableSource<? extends R>> var1, int var2, int var3, boolean var4) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.verifyPositive(var2, "maxConcurrency");
      ObjectHelper.verifyPositive(var3, "prefetch");
      ErrorMode var5;
      if(var4) {
         var5 = ErrorMode.END;
      } else {
         var5 = ErrorMode.BOUNDARY;
      }

      return RxJavaPlugins.onAssembly(new ObservableConcatMapEager(this, var1, var5, var2, var3));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> concatMapEagerDelayError(Function<? super T, ? extends ObservableSource<? extends R>> var1, boolean var2) {
      return this.concatMapEagerDelayError(var1, Integer.MAX_VALUE, bufferSize(), var2);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Observable<U> concatMapIterable(Function<? super T, ? extends Iterable<? extends U>> var1) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      return RxJavaPlugins.onAssembly(new ObservableFlattenIterable(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Observable<U> concatMapIterable(Function<? super T, ? extends Iterable<? extends U>> var1, int var2) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.verifyPositive(var2, "prefetch");
      return this.concatMap(ObservableInternalHelper.flatMapIntoIterable(var1), var2);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> concatWith(ObservableSource<? extends T> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return concat(this, var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<Boolean> contains(Object var1) {
      ObjectHelper.requireNonNull(var1, "element is null");
      return this.any(Functions.equalsWith(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<Long> count() {
      return RxJavaPlugins.onAssembly(new ObservableCountSingle(this));
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Observable<T> debounce(long var1, TimeUnit var3) {
      return this.debounce(var1, var3, Schedulers.computation());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<T> debounce(long var1, TimeUnit var3, Scheduler var4) {
      ObjectHelper.requireNonNull(var3, "unit is null");
      ObjectHelper.requireNonNull(var4, "scheduler is null");
      return RxJavaPlugins.onAssembly(new ObservableDebounceTimed(this, var1, var3, var4));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Observable<T> debounce(Function<? super T, ? extends ObservableSource<U>> var1) {
      ObjectHelper.requireNonNull(var1, "debounceSelector is null");
      return RxJavaPlugins.onAssembly(new ObservableDebounce(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> defaultIfEmpty(T var1) {
      ObjectHelper.requireNonNull(var1, "defaultItem is null");
      return this.switchIfEmpty(just(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Observable<T> delay(long var1, TimeUnit var3) {
      return this.delay(var1, var3, Schedulers.computation(), false);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<T> delay(long var1, TimeUnit var3, Scheduler var4) {
      return this.delay(var1, var3, var4, false);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<T> delay(long var1, TimeUnit var3, Scheduler var4, boolean var5) {
      ObjectHelper.requireNonNull(var3, "unit is null");
      ObjectHelper.requireNonNull(var4, "scheduler is null");
      return RxJavaPlugins.onAssembly(new ObservableDelay(this, var1, var3, var4, var5));
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Observable<T> delay(long var1, TimeUnit var3, boolean var4) {
      return this.delay(var1, var3, Schedulers.computation(), var4);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, V extends Object> Observable<T> delay(ObservableSource<U> var1, Function<? super T, ? extends ObservableSource<V>> var2) {
      return this.delaySubscription(var1).delay(var2);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Observable<T> delay(Function<? super T, ? extends ObservableSource<U>> var1) {
      ObjectHelper.requireNonNull(var1, "itemDelay is null");
      return this.flatMap(ObservableInternalHelper.itemDelay(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Observable<T> delaySubscription(long var1, TimeUnit var3) {
      return this.delaySubscription(var1, var3, Schedulers.computation());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<T> delaySubscription(long var1, TimeUnit var3, Scheduler var4) {
      return this.delaySubscription(timer(var1, var3, var4));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Observable<T> delaySubscription(ObservableSource<U> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return RxJavaPlugins.onAssembly(new ObservableDelaySubscriptionOther(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T2 extends Object> Observable<T2> dematerialize() {
      return RxJavaPlugins.onAssembly(new ObservableDematerialize(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> distinct() {
      return this.distinct(Functions.identity(), Functions.createHashSet());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <K extends Object> Observable<T> distinct(Function<? super T, K> var1) {
      return this.distinct(var1, Functions.createHashSet());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <K extends Object> Observable<T> distinct(Function<? super T, K> var1, Callable<? extends Collection<? super K>> var2) {
      ObjectHelper.requireNonNull(var1, "keySelector is null");
      ObjectHelper.requireNonNull(var2, "collectionSupplier is null");
      return RxJavaPlugins.onAssembly(new ObservableDistinct(this, var1, var2));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> distinctUntilChanged() {
      return this.distinctUntilChanged(Functions.identity());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> distinctUntilChanged(BiPredicate<? super T, ? super T> var1) {
      ObjectHelper.requireNonNull(var1, "comparer is null");
      return RxJavaPlugins.onAssembly(new ObservableDistinctUntilChanged(this, Functions.identity(), var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <K extends Object> Observable<T> distinctUntilChanged(Function<? super T, K> var1) {
      ObjectHelper.requireNonNull(var1, "keySelector is null");
      return RxJavaPlugins.onAssembly(new ObservableDistinctUntilChanged(this, var1, ObjectHelper.equalsPredicate()));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> doAfterNext(Consumer<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "onAfterNext is null");
      return RxJavaPlugins.onAssembly(new ObservableDoAfterNext(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> doAfterTerminate(Action var1) {
      ObjectHelper.requireNonNull(var1, "onFinally is null");
      return this.doOnEach(Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> doFinally(Action var1) {
      ObjectHelper.requireNonNull(var1, "onFinally is null");
      return RxJavaPlugins.onAssembly(new ObservableDoFinally(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> doOnComplete(Action var1) {
      return this.doOnEach(Functions.emptyConsumer(), Functions.emptyConsumer(), var1, Functions.EMPTY_ACTION);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> doOnDispose(Action var1) {
      return this.doOnLifecycle(Functions.emptyConsumer(), var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> doOnEach(Observer<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "observer is null");
      return this.doOnEach(ObservableInternalHelper.observerOnNext(var1), ObservableInternalHelper.observerOnError(var1), ObservableInternalHelper.observerOnComplete(var1), Functions.EMPTY_ACTION);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> doOnEach(Consumer<? super Notification<T>> var1) {
      ObjectHelper.requireNonNull(var1, "consumer is null");
      return this.doOnEach(Functions.notificationOnNext(var1), Functions.notificationOnError(var1), Functions.notificationOnComplete(var1), Functions.EMPTY_ACTION);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> doOnError(Consumer<? super Throwable> var1) {
      return this.doOnEach(Functions.emptyConsumer(), var1, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> doOnLifecycle(Consumer<? super Disposable> var1, Action var2) {
      ObjectHelper.requireNonNull(var1, "onSubscribe is null");
      ObjectHelper.requireNonNull(var2, "onDispose is null");
      return RxJavaPlugins.onAssembly(new ObservableDoOnLifecycle(this, var1, var2));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> doOnNext(Consumer<? super T> var1) {
      return this.doOnEach(var1, Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> doOnSubscribe(Consumer<? super Disposable> var1) {
      return this.doOnLifecycle(var1, Functions.EMPTY_ACTION);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> doOnTerminate(Action var1) {
      ObjectHelper.requireNonNull(var1, "onTerminate is null");
      return this.doOnEach(Functions.emptyConsumer(), Functions.actionConsumer(var1), var1, Functions.EMPTY_ACTION);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> elementAt(long var1) {
      if(var1 < 0L) {
         StringBuilder var3 = new StringBuilder();
         var3.append("index >= 0 required but it was ");
         var3.append(var1);
         throw new IndexOutOfBoundsException(var3.toString());
      } else {
         return RxJavaPlugins.onAssembly(new ObservableElementAtMaybe(this, var1));
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> elementAt(long var1, T var3) {
      if(var1 < 0L) {
         StringBuilder var4 = new StringBuilder();
         var4.append("index >= 0 required but it was ");
         var4.append(var1);
         throw new IndexOutOfBoundsException(var4.toString());
      } else {
         ObjectHelper.requireNonNull(var3, "defaultItem is null");
         return RxJavaPlugins.onAssembly(new ObservableElementAtSingle(this, var1, var3));
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> elementAtOrError(long var1) {
      if(var1 < 0L) {
         StringBuilder var3 = new StringBuilder();
         var3.append("index >= 0 required but it was ");
         var3.append(var1);
         throw new IndexOutOfBoundsException(var3.toString());
      } else {
         return RxJavaPlugins.onAssembly(new ObservableElementAtSingle(this, var1, (Object)null));
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> filter(Predicate<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "predicate is null");
      return RxJavaPlugins.onAssembly(new ObservableFilter(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> first(T var1) {
      return this.elementAt(0L, var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> firstElement() {
      return this.elementAt(0L);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> firstOrError() {
      return this.elementAtOrError(0L);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> var1) {
      return this.flatMap(var1, false);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> var1, int var2) {
      return this.flatMap(var1, false, var2, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, R extends Object> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends U>> var1, BiFunction<? super T, ? super U, ? extends R> var2) {
      return this.flatMap(var1, var2, false, bufferSize(), bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, R extends Object> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends U>> var1, BiFunction<? super T, ? super U, ? extends R> var2, int var3) {
      return this.flatMap(var1, var2, false, var3, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, R extends Object> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends U>> var1, BiFunction<? super T, ? super U, ? extends R> var2, boolean var3) {
      return this.flatMap(var1, var2, var3, bufferSize(), bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, R extends Object> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends U>> var1, BiFunction<? super T, ? super U, ? extends R> var2, boolean var3, int var4) {
      return this.flatMap(var1, var2, var3, var4, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, R extends Object> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends U>> var1, BiFunction<? super T, ? super U, ? extends R> var2, boolean var3, int var4, int var5) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.requireNonNull(var2, "combiner is null");
      return this.flatMap(ObservableInternalHelper.flatMapWithCombiner(var1, var2), var3, var4, var5);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> var1, Function<? super Throwable, ? extends ObservableSource<? extends R>> var2, Callable<? extends ObservableSource<? extends R>> var3) {
      ObjectHelper.requireNonNull(var1, "onNextMapper is null");
      ObjectHelper.requireNonNull(var2, "onErrorMapper is null");
      ObjectHelper.requireNonNull(var3, "onCompleteSupplier is null");
      return merge((ObservableSource)(new ObservableMapNotification(this, var1, var2, var3)));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> var1, Function<Throwable, ? extends ObservableSource<? extends R>> var2, Callable<? extends ObservableSource<? extends R>> var3, int var4) {
      ObjectHelper.requireNonNull(var1, "onNextMapper is null");
      ObjectHelper.requireNonNull(var2, "onErrorMapper is null");
      ObjectHelper.requireNonNull(var3, "onCompleteSupplier is null");
      return merge((ObservableSource)(new ObservableMapNotification(this, var1, var2, var3)), var4);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> var1, boolean var2) {
      return this.flatMap(var1, var2, Integer.MAX_VALUE);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> var1, boolean var2, int var3) {
      return this.flatMap(var1, var2, var3, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> var1, boolean var2, int var3, int var4) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.verifyPositive(var3, "maxConcurrency");
      ObjectHelper.verifyPositive(var4, "bufferSize");
      if(this instanceof ScalarCallable) {
         Object var5 = ((ScalarCallable)this).call();
         return var5 == null?empty():ObservableScalarXMap.scalarXMap(var5, var1);
      } else {
         return RxJavaPlugins.onAssembly(new ObservableFlatMap(this, var1, var2, var3, var4));
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable flatMapCompletable(Function<? super T, ? extends CompletableSource> var1) {
      return this.flatMapCompletable(var1, false);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable flatMapCompletable(Function<? super T, ? extends CompletableSource> var1, boolean var2) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      return RxJavaPlugins.onAssembly(new ObservableFlatMapCompletableCompletable(this, var1, var2));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Observable<U> flatMapIterable(Function<? super T, ? extends Iterable<? extends U>> var1) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      return RxJavaPlugins.onAssembly(new ObservableFlattenIterable(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, V extends Object> Observable<V> flatMapIterable(Function<? super T, ? extends Iterable<? extends U>> var1, BiFunction<? super T, ? super U, ? extends V> var2) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.requireNonNull(var2, "resultSelector is null");
      return this.flatMap(ObservableInternalHelper.flatMapIntoIterable(var1), var2, false, bufferSize(), bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> flatMapMaybe(Function<? super T, ? extends MaybeSource<? extends R>> var1) {
      return this.flatMapMaybe(var1, false);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> flatMapMaybe(Function<? super T, ? extends MaybeSource<? extends R>> var1, boolean var2) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      return RxJavaPlugins.onAssembly(new ObservableFlatMapMaybe(this, var1, var2));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> flatMapSingle(Function<? super T, ? extends SingleSource<? extends R>> var1) {
      return this.flatMapSingle(var1, false);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> flatMapSingle(Function<? super T, ? extends SingleSource<? extends R>> var1, boolean var2) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      return RxJavaPlugins.onAssembly(new ObservableFlatMapSingle(this, var1, var2));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Disposable forEach(Consumer<? super T> var1) {
      return this.subscribe(var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Disposable forEachWhile(Predicate<? super T> var1) {
      return this.forEachWhile(var1, Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Disposable forEachWhile(Predicate<? super T> var1, Consumer<? super Throwable> var2) {
      return this.forEachWhile(var1, var2, Functions.EMPTY_ACTION);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Disposable forEachWhile(Predicate<? super T> var1, Consumer<? super Throwable> var2, Action var3) {
      ObjectHelper.requireNonNull(var1, "onNext is null");
      ObjectHelper.requireNonNull(var2, "onError is null");
      ObjectHelper.requireNonNull(var3, "onComplete is null");
      ForEachWhileObserver var4 = new ForEachWhileObserver(var1, var2, var3);
      this.subscribe((Observer)var4);
      return var4;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <K extends Object> Observable<GroupedObservable<K, T>> groupBy(Function<? super T, ? extends K> var1) {
      return this.groupBy(var1, Functions.identity(), false, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <K extends Object, V extends Object> Observable<GroupedObservable<K, V>> groupBy(Function<? super T, ? extends K> var1, Function<? super T, ? extends V> var2) {
      return this.groupBy(var1, var2, false, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <K extends Object, V extends Object> Observable<GroupedObservable<K, V>> groupBy(Function<? super T, ? extends K> var1, Function<? super T, ? extends V> var2, boolean var3) {
      return this.groupBy(var1, var2, var3, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <K extends Object, V extends Object> Observable<GroupedObservable<K, V>> groupBy(Function<? super T, ? extends K> var1, Function<? super T, ? extends V> var2, boolean var3, int var4) {
      ObjectHelper.requireNonNull(var1, "keySelector is null");
      ObjectHelper.requireNonNull(var2, "valueSelector is null");
      ObjectHelper.verifyPositive(var4, "bufferSize");
      return RxJavaPlugins.onAssembly(new ObservableGroupBy(this, var1, var2, var4, var3));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <K extends Object> Observable<GroupedObservable<K, T>> groupBy(Function<? super T, ? extends K> var1, boolean var2) {
      return this.groupBy(var1, Functions.identity(), var2, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <TRight extends Object, TLeftEnd extends Object, TRightEnd extends Object, R extends Object> Observable<R> groupJoin(ObservableSource<? extends TRight> var1, Function<? super T, ? extends ObservableSource<TLeftEnd>> var2, Function<? super TRight, ? extends ObservableSource<TRightEnd>> var3, BiFunction<? super T, ? super Observable<TRight>, ? extends R> var4) {
      ObjectHelper.requireNonNull(var1, "other is null");
      ObjectHelper.requireNonNull(var2, "leftEnd is null");
      ObjectHelper.requireNonNull(var3, "rightEnd is null");
      ObjectHelper.requireNonNull(var4, "resultSelector is null");
      return RxJavaPlugins.onAssembly(new ObservableGroupJoin(this, var1, var2, var3, var4));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> hide() {
      return RxJavaPlugins.onAssembly(new ObservableHide(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable ignoreElements() {
      return RxJavaPlugins.onAssembly(new ObservableIgnoreElementsCompletable(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<Boolean> isEmpty() {
      return this.all(Functions.alwaysFalse());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <TRight extends Object, TLeftEnd extends Object, TRightEnd extends Object, R extends Object> Observable<R> join(ObservableSource<? extends TRight> var1, Function<? super T, ? extends ObservableSource<TLeftEnd>> var2, Function<? super TRight, ? extends ObservableSource<TRightEnd>> var3, BiFunction<? super T, ? super TRight, ? extends R> var4) {
      ObjectHelper.requireNonNull(var1, "other is null");
      ObjectHelper.requireNonNull(var2, "leftEnd is null");
      ObjectHelper.requireNonNull(var3, "rightEnd is null");
      ObjectHelper.requireNonNull(var4, "resultSelector is null");
      return RxJavaPlugins.onAssembly(new ObservableJoin(this, var1, var2, var3, var4));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> last(T var1) {
      ObjectHelper.requireNonNull(var1, "defaultItem is null");
      return RxJavaPlugins.onAssembly(new ObservableLastSingle(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> lastElement() {
      return RxJavaPlugins.onAssembly(new ObservableLastMaybe(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> lastOrError() {
      return RxJavaPlugins.onAssembly(new ObservableLastSingle(this, (Object)null));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> lift(ObservableOperator<? extends R, ? super T> var1) {
      ObjectHelper.requireNonNull(var1, "onLift is null");
      return RxJavaPlugins.onAssembly(new ObservableLift(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> map(Function<? super T, ? extends R> var1) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      return RxJavaPlugins.onAssembly(new ObservableMap(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<Notification<T>> materialize() {
      return RxJavaPlugins.onAssembly(new ObservableMaterialize(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> mergeWith(ObservableSource<? extends T> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return merge(this, var1);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<T> observeOn(Scheduler var1) {
      return this.observeOn(var1, false, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<T> observeOn(Scheduler var1, boolean var2) {
      return this.observeOn(var1, var2, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<T> observeOn(Scheduler var1, boolean var2, int var3) {
      ObjectHelper.requireNonNull(var1, "scheduler is null");
      ObjectHelper.verifyPositive(var3, "bufferSize");
      return RxJavaPlugins.onAssembly(new ObservableObserveOn(this, var1, var2, var3));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Observable<U> ofType(Class<U> var1) {
      ObjectHelper.requireNonNull(var1, "clazz is null");
      return this.filter(Functions.isInstanceOf(var1)).cast(var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> onErrorResumeNext(ObservableSource<? extends T> var1) {
      ObjectHelper.requireNonNull(var1, "next is null");
      return this.onErrorResumeNext(Functions.justFunction(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> onErrorResumeNext(Function<? super Throwable, ? extends ObservableSource<? extends T>> var1) {
      ObjectHelper.requireNonNull(var1, "resumeFunction is null");
      return RxJavaPlugins.onAssembly(new ObservableOnErrorNext(this, var1, false));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> onErrorReturn(Function<? super Throwable, ? extends T> var1) {
      ObjectHelper.requireNonNull(var1, "valueSupplier is null");
      return RxJavaPlugins.onAssembly(new ObservableOnErrorReturn(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> onErrorReturnItem(T var1) {
      ObjectHelper.requireNonNull(var1, "item is null");
      return this.onErrorReturn(Functions.justFunction(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> onExceptionResumeNext(ObservableSource<? extends T> var1) {
      ObjectHelper.requireNonNull(var1, "next is null");
      return RxJavaPlugins.onAssembly(new ObservableOnErrorNext(this, Functions.justFunction(var1), true));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> onTerminateDetach() {
      return RxJavaPlugins.onAssembly(new ObservableDetach(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> publish(Function<? super Observable<T>, ? extends ObservableSource<R>> var1) {
      ObjectHelper.requireNonNull(var1, "selector is null");
      return RxJavaPlugins.onAssembly(new ObservablePublishSelector(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final ConnectableObservable<T> publish() {
      return ObservablePublish.create(this);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> reduce(BiFunction<T, T, T> var1) {
      ObjectHelper.requireNonNull(var1, "reducer is null");
      return RxJavaPlugins.onAssembly(new ObservableReduceMaybe(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Single<R> reduce(R var1, BiFunction<R, ? super T, R> var2) {
      ObjectHelper.requireNonNull(var1, "seed is null");
      ObjectHelper.requireNonNull(var2, "reducer is null");
      return RxJavaPlugins.onAssembly(new ObservableReduceSeedSingle(this, var1, var2));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Single<R> reduceWith(Callable<R> var1, BiFunction<R, ? super T, R> var2) {
      ObjectHelper.requireNonNull(var1, "seedSupplier is null");
      ObjectHelper.requireNonNull(var2, "reducer is null");
      return RxJavaPlugins.onAssembly(new ObservableReduceWithSingle(this, var1, var2));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> repeat() {
      return this.repeat(Long.MAX_VALUE);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> repeat(long var1) {
      if(var1 < 0L) {
         StringBuilder var3 = new StringBuilder();
         var3.append("times >= 0 required but it was ");
         var3.append(var1);
         throw new IllegalArgumentException(var3.toString());
      } else {
         return var1 == 0L?empty():RxJavaPlugins.onAssembly(new ObservableRepeat(this, var1));
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> repeatUntil(BooleanSupplier var1) {
      ObjectHelper.requireNonNull(var1, "stop is null");
      return RxJavaPlugins.onAssembly(new ObservableRepeatUntil(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> repeatWhen(Function<? super Observable<Object>, ? extends ObservableSource<?>> var1) {
      ObjectHelper.requireNonNull(var1, "handler is null");
      return RxJavaPlugins.onAssembly(new ObservableRepeatWhen(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> replay(Function<? super Observable<T>, ? extends ObservableSource<R>> var1) {
      ObjectHelper.requireNonNull(var1, "selector is null");
      return ObservableReplay.multicastSelector(ObservableInternalHelper.replayCallable(this), var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> replay(Function<? super Observable<T>, ? extends ObservableSource<R>> var1, int var2) {
      ObjectHelper.requireNonNull(var1, "selector is null");
      ObjectHelper.verifyPositive(var2, "bufferSize");
      return ObservableReplay.multicastSelector(ObservableInternalHelper.replayCallable(this, var2), var1);
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final <R extends Object> Observable<R> replay(Function<? super Observable<T>, ? extends ObservableSource<R>> var1, int var2, long var3, TimeUnit var5) {
      return this.replay(var1, var2, var3, var5, Schedulers.computation());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final <R extends Object> Observable<R> replay(Function<? super Observable<T>, ? extends ObservableSource<R>> var1, int var2, long var3, TimeUnit var5, Scheduler var6) {
      ObjectHelper.requireNonNull(var1, "selector is null");
      ObjectHelper.verifyPositive(var2, "bufferSize");
      ObjectHelper.requireNonNull(var5, "unit is null");
      ObjectHelper.requireNonNull(var6, "scheduler is null");
      return ObservableReplay.multicastSelector(ObservableInternalHelper.replayCallable(this, var2, var3, var5, var6), var1);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final <R extends Object> Observable<R> replay(Function<? super Observable<T>, ? extends ObservableSource<R>> var1, int var2, Scheduler var3) {
      ObjectHelper.requireNonNull(var1, "selector is null");
      ObjectHelper.requireNonNull(var3, "scheduler is null");
      ObjectHelper.verifyPositive(var2, "bufferSize");
      return ObservableReplay.multicastSelector(ObservableInternalHelper.replayCallable(this, var2), ObservableInternalHelper.replayFunction(var1, var3));
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final <R extends Object> Observable<R> replay(Function<? super Observable<T>, ? extends ObservableSource<R>> var1, long var2, TimeUnit var4) {
      return this.replay(var1, var2, var4, Schedulers.computation());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final <R extends Object> Observable<R> replay(Function<? super Observable<T>, ? extends ObservableSource<R>> var1, long var2, TimeUnit var4, Scheduler var5) {
      ObjectHelper.requireNonNull(var1, "selector is null");
      ObjectHelper.requireNonNull(var4, "unit is null");
      ObjectHelper.requireNonNull(var5, "scheduler is null");
      return ObservableReplay.multicastSelector(ObservableInternalHelper.replayCallable(this, var2, var4, var5), var1);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final <R extends Object> Observable<R> replay(Function<? super Observable<T>, ? extends ObservableSource<R>> var1, Scheduler var2) {
      ObjectHelper.requireNonNull(var1, "selector is null");
      ObjectHelper.requireNonNull(var2, "scheduler is null");
      return ObservableReplay.multicastSelector(ObservableInternalHelper.replayCallable(this), ObservableInternalHelper.replayFunction(var1, var2));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final ConnectableObservable<T> replay() {
      return ObservableReplay.createFrom(this);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final ConnectableObservable<T> replay(int var1) {
      ObjectHelper.verifyPositive(var1, "bufferSize");
      return ObservableReplay.create(this, var1);
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final ConnectableObservable<T> replay(int var1, long var2, TimeUnit var4) {
      return this.replay(var1, var2, var4, Schedulers.computation());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final ConnectableObservable<T> replay(int var1, long var2, TimeUnit var4, Scheduler var5) {
      ObjectHelper.verifyPositive(var1, "bufferSize");
      ObjectHelper.requireNonNull(var4, "unit is null");
      ObjectHelper.requireNonNull(var5, "scheduler is null");
      return ObservableReplay.create(this, var2, var4, var5, var1);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final ConnectableObservable<T> replay(int var1, Scheduler var2) {
      ObjectHelper.verifyPositive(var1, "bufferSize");
      return ObservableReplay.observeOn(this.replay(var1), var2);
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final ConnectableObservable<T> replay(long var1, TimeUnit var3) {
      return this.replay(var1, var3, Schedulers.computation());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final ConnectableObservable<T> replay(long var1, TimeUnit var3, Scheduler var4) {
      ObjectHelper.requireNonNull(var3, "unit is null");
      ObjectHelper.requireNonNull(var4, "scheduler is null");
      return ObservableReplay.create(this, var1, var3, var4);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final ConnectableObservable<T> replay(Scheduler var1) {
      ObjectHelper.requireNonNull(var1, "scheduler is null");
      return ObservableReplay.observeOn(this.replay(), var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> retry() {
      return this.retry(Long.MAX_VALUE, Functions.alwaysTrue());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> retry(long var1) {
      return this.retry(var1, Functions.alwaysTrue());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> retry(long var1, Predicate<? super Throwable> var3) {
      if(var1 < 0L) {
         StringBuilder var4 = new StringBuilder();
         var4.append("times >= 0 required but it was ");
         var4.append(var1);
         throw new IllegalArgumentException(var4.toString());
      } else {
         ObjectHelper.requireNonNull(var3, "predicate is null");
         return RxJavaPlugins.onAssembly(new ObservableRetryPredicate(this, var1, var3));
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> retry(BiPredicate<? super Integer, ? super Throwable> var1) {
      ObjectHelper.requireNonNull(var1, "predicate is null");
      return RxJavaPlugins.onAssembly(new ObservableRetryBiPredicate(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> retry(Predicate<? super Throwable> var1) {
      return this.retry(Long.MAX_VALUE, var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> retryUntil(BooleanSupplier var1) {
      ObjectHelper.requireNonNull(var1, "stop is null");
      return this.retry(Long.MAX_VALUE, Functions.predicateReverseFor(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> retryWhen(Function<? super Observable<Throwable>, ? extends ObservableSource<?>> var1) {
      ObjectHelper.requireNonNull(var1, "handler is null");
      return RxJavaPlugins.onAssembly(new ObservableRetryWhen(this, var1));
   }

   @SchedulerSupport("none")
   public final void safeSubscribe(Observer<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "s is null");
      if(var1 instanceof SafeObserver) {
         this.subscribe(var1);
      } else {
         this.subscribe((Observer)(new SafeObserver(var1)));
      }
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Observable<T> sample(long var1, TimeUnit var3) {
      return this.sample(var1, var3, Schedulers.computation());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<T> sample(long var1, TimeUnit var3, Scheduler var4) {
      ObjectHelper.requireNonNull(var3, "unit is null");
      ObjectHelper.requireNonNull(var4, "scheduler is null");
      return RxJavaPlugins.onAssembly(new ObservableSampleTimed(this, var1, var3, var4, false));
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<T> sample(long var1, TimeUnit var3, Scheduler var4, boolean var5) {
      ObjectHelper.requireNonNull(var3, "unit is null");
      ObjectHelper.requireNonNull(var4, "scheduler is null");
      return RxJavaPlugins.onAssembly(new ObservableSampleTimed(this, var1, var3, var4, var5));
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Observable<T> sample(long var1, TimeUnit var3, boolean var4) {
      return this.sample(var1, var3, Schedulers.computation(), var4);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Observable<T> sample(ObservableSource<U> var1) {
      ObjectHelper.requireNonNull(var1, "sampler is null");
      return RxJavaPlugins.onAssembly(new ObservableSampleWithObservable(this, var1, false));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Observable<T> sample(ObservableSource<U> var1, boolean var2) {
      ObjectHelper.requireNonNull(var1, "sampler is null");
      return RxJavaPlugins.onAssembly(new ObservableSampleWithObservable(this, var1, var2));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> scan(BiFunction<T, T, T> var1) {
      ObjectHelper.requireNonNull(var1, "accumulator is null");
      return RxJavaPlugins.onAssembly(new ObservableScan(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> scan(R var1, BiFunction<R, ? super T, R> var2) {
      ObjectHelper.requireNonNull(var1, "seed is null");
      return this.scanWith(Functions.justCallable(var1), var2);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> scanWith(Callable<R> var1, BiFunction<R, ? super T, R> var2) {
      ObjectHelper.requireNonNull(var1, "seedSupplier is null");
      ObjectHelper.requireNonNull(var2, "accumulator is null");
      return RxJavaPlugins.onAssembly(new ObservableScanSeed(this, var1, var2));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> serialize() {
      return RxJavaPlugins.onAssembly(new ObservableSerialized(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> share() {
      return this.publish().refCount();
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> single(T var1) {
      ObjectHelper.requireNonNull(var1, "defaultItem is null");
      return RxJavaPlugins.onAssembly(new ObservableSingleSingle(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> singleElement() {
      return RxJavaPlugins.onAssembly(new ObservableSingleMaybe(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> singleOrError() {
      return RxJavaPlugins.onAssembly(new ObservableSingleSingle(this, (Object)null));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> skip(long var1) {
      return var1 <= 0L?RxJavaPlugins.onAssembly(this):RxJavaPlugins.onAssembly(new ObservableSkip(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Observable<T> skip(long var1, TimeUnit var3) {
      return this.skipUntil(timer(var1, var3));
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<T> skip(long var1, TimeUnit var3, Scheduler var4) {
      return this.skipUntil(timer(var1, var3, var4));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> skipLast(int var1) {
      if(var1 < 0) {
         StringBuilder var2 = new StringBuilder();
         var2.append("count >= 0 required but it was ");
         var2.append(var1);
         throw new IndexOutOfBoundsException(var2.toString());
      } else {
         return var1 == 0?RxJavaPlugins.onAssembly(this):RxJavaPlugins.onAssembly(new ObservableSkipLast(this, var1));
      }
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:trampoline")
   public final Observable<T> skipLast(long var1, TimeUnit var3) {
      return this.skipLast(var1, var3, Schedulers.trampoline(), false, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<T> skipLast(long var1, TimeUnit var3, Scheduler var4) {
      return this.skipLast(var1, var3, var4, false, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<T> skipLast(long var1, TimeUnit var3, Scheduler var4, boolean var5) {
      return this.skipLast(var1, var3, var4, var5, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<T> skipLast(long var1, TimeUnit var3, Scheduler var4, boolean var5, int var6) {
      ObjectHelper.requireNonNull(var3, "unit is null");
      ObjectHelper.requireNonNull(var4, "scheduler is null");
      ObjectHelper.verifyPositive(var6, "bufferSize");
      return RxJavaPlugins.onAssembly(new ObservableSkipLastTimed(this, var1, var3, var4, var6 << 1, var5));
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:trampoline")
   public final Observable<T> skipLast(long var1, TimeUnit var3, boolean var4) {
      return this.skipLast(var1, var3, Schedulers.trampoline(), var4, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Observable<T> skipUntil(ObservableSource<U> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return RxJavaPlugins.onAssembly(new ObservableSkipUntil(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> skipWhile(Predicate<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "predicate is null");
      return RxJavaPlugins.onAssembly(new ObservableSkipWhile(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> sorted() {
      return this.toList().toObservable().map(Functions.listSorter(Functions.naturalComparator())).flatMapIterable(Functions.identity());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> sorted(Comparator<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "sortFunction is null");
      return this.toList().toObservable().map(Functions.listSorter(var1)).flatMapIterable(Functions.identity());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> startWith(ObservableSource<? extends T> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return concatArray(new ObservableSource[]{var1, this});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> startWith(Iterable<? extends T> var1) {
      return concatArray(new ObservableSource[]{fromIterable(var1), this});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> startWith(T var1) {
      ObjectHelper.requireNonNull(var1, "item is null");
      return concatArray(new ObservableSource[]{just(var1), this});
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> startWithArray(T ... var1) {
      Observable var2 = fromArray(var1);
      return var2 == empty()?RxJavaPlugins.onAssembly(this):concatArray(new ObservableSource[]{var2, this});
   }

   @SchedulerSupport("none")
   public final Disposable subscribe() {
      return this.subscribe(Functions.emptyConsumer(), Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION, Functions.emptyConsumer());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Disposable subscribe(Consumer<? super T> var1) {
      return this.subscribe(var1, Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION, Functions.emptyConsumer());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Disposable subscribe(Consumer<? super T> var1, Consumer<? super Throwable> var2) {
      return this.subscribe(var1, var2, Functions.EMPTY_ACTION, Functions.emptyConsumer());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Disposable subscribe(Consumer<? super T> var1, Consumer<? super Throwable> var2, Action var3) {
      return this.subscribe(var1, var2, var3, Functions.emptyConsumer());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Disposable subscribe(Consumer<? super T> var1, Consumer<? super Throwable> var2, Action var3, Consumer<? super Disposable> var4) {
      ObjectHelper.requireNonNull(var1, "onNext is null");
      ObjectHelper.requireNonNull(var2, "onError is null");
      ObjectHelper.requireNonNull(var3, "onComplete is null");
      ObjectHelper.requireNonNull(var4, "onSubscribe is null");
      LambdaObserver var5 = new LambdaObserver(var1, var2, var3, var4);
      this.subscribe((Observer)var5);
      return var5;
   }

   @SchedulerSupport("none")
   public final void subscribe(Observer<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "observer is null");

      try {
         var1 = RxJavaPlugins.onSubscribe(this, var1);
         ObjectHelper.requireNonNull(var1, "Plugin returned null Observer");
         this.subscribeActual(var1);
      } catch (NullPointerException var3) {
         throw var3;
      } catch (Throwable var4) {
         Exceptions.throwIfFatal(var4);
         RxJavaPlugins.onError(var4);
         NullPointerException var2 = new NullPointerException("Actually not, but can\'t throw other exceptions due to RS");
         var2.initCause(var4);
         throw var2;
      }
   }

   public abstract void subscribeActual(Observer<? super T> var1);

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<T> subscribeOn(Scheduler var1) {
      ObjectHelper.requireNonNull(var1, "scheduler is null");
      return RxJavaPlugins.onAssembly(new ObservableSubscribeOn(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <E extends Object & Observer<? super T>> E subscribeWith(E var1) {
      this.subscribe(var1);
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> switchIfEmpty(ObservableSource<? extends T> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return RxJavaPlugins.onAssembly(new ObservableSwitchIfEmpty(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> switchMap(Function<? super T, ? extends ObservableSource<? extends R>> var1) {
      return this.switchMap(var1, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> switchMap(Function<? super T, ? extends ObservableSource<? extends R>> var1, int var2) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.verifyPositive(var2, "bufferSize");
      if(this instanceof ScalarCallable) {
         Object var3 = ((ScalarCallable)this).call();
         return var3 == null?empty():ObservableScalarXMap.scalarXMap(var3, var1);
      } else {
         return RxJavaPlugins.onAssembly(new ObservableSwitchMap(this, var1, var2, false));
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> switchMapDelayError(Function<? super T, ? extends ObservableSource<? extends R>> var1) {
      return this.switchMapDelayError(var1, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> switchMapDelayError(Function<? super T, ? extends ObservableSource<? extends R>> var1, int var2) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.verifyPositive(var2, "bufferSize");
      if(this instanceof ScalarCallable) {
         Object var3 = ((ScalarCallable)this).call();
         return var3 == null?empty():ObservableScalarXMap.scalarXMap(var3, var1);
      } else {
         return RxJavaPlugins.onAssembly(new ObservableSwitchMap(this, var1, var2, true));
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   @Experimental
   @NonNull
   public final <R extends Object> Observable<R> switchMapSingle(@NonNull Function<? super T, ? extends SingleSource<? extends R>> var1) {
      return ObservableInternalHelper.switchMapSingle(this, var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   @Experimental
   @NonNull
   public final <R extends Object> Observable<R> switchMapSingleDelayError(@NonNull Function<? super T, ? extends SingleSource<? extends R>> var1) {
      return ObservableInternalHelper.switchMapSingleDelayError(this, var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> take(long var1) {
      if(var1 < 0L) {
         StringBuilder var3 = new StringBuilder();
         var3.append("count >= 0 required but it was ");
         var3.append(var1);
         throw new IllegalArgumentException(var3.toString());
      } else {
         return RxJavaPlugins.onAssembly(new ObservableTake(this, var1));
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> take(long var1, TimeUnit var3) {
      return this.takeUntil((ObservableSource)timer(var1, var3));
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<T> take(long var1, TimeUnit var3, Scheduler var4) {
      return this.takeUntil((ObservableSource)timer(var1, var3, var4));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> takeLast(int var1) {
      if(var1 < 0) {
         StringBuilder var2 = new StringBuilder();
         var2.append("count >= 0 required but it was ");
         var2.append(var1);
         throw new IndexOutOfBoundsException(var2.toString());
      } else {
         return var1 == 0?RxJavaPlugins.onAssembly(new ObservableIgnoreElements(this)):(var1 == 1?RxJavaPlugins.onAssembly(new ObservableTakeLastOne(this)):RxJavaPlugins.onAssembly(new ObservableTakeLast(this, var1)));
      }
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:trampoline")
   public final Observable<T> takeLast(long var1, long var3, TimeUnit var5) {
      return this.takeLast(var1, var3, var5, Schedulers.trampoline(), false, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<T> takeLast(long var1, long var3, TimeUnit var5, Scheduler var6) {
      return this.takeLast(var1, var3, var5, var6, false, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<T> takeLast(long var1, long var3, TimeUnit var5, Scheduler var6, boolean var7, int var8) {
      ObjectHelper.requireNonNull(var5, "unit is null");
      ObjectHelper.requireNonNull(var6, "scheduler is null");
      ObjectHelper.verifyPositive(var8, "bufferSize");
      if(var1 < 0L) {
         StringBuilder var9 = new StringBuilder();
         var9.append("count >= 0 required but it was ");
         var9.append(var1);
         throw new IndexOutOfBoundsException(var9.toString());
      } else {
         return RxJavaPlugins.onAssembly(new ObservableTakeLastTimed(this, var1, var3, var5, var6, var8, var7));
      }
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:trampoline")
   public final Observable<T> takeLast(long var1, TimeUnit var3) {
      return this.takeLast(var1, var3, Schedulers.trampoline(), false, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<T> takeLast(long var1, TimeUnit var3, Scheduler var4) {
      return this.takeLast(var1, var3, var4, false, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<T> takeLast(long var1, TimeUnit var3, Scheduler var4, boolean var5) {
      return this.takeLast(var1, var3, var4, var5, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<T> takeLast(long var1, TimeUnit var3, Scheduler var4, boolean var5, int var6) {
      return this.takeLast(Long.MAX_VALUE, var1, var3, var4, var5, var6);
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:trampoline")
   public final Observable<T> takeLast(long var1, TimeUnit var3, boolean var4) {
      return this.takeLast(var1, var3, Schedulers.trampoline(), var4, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Observable<T> takeUntil(ObservableSource<U> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return RxJavaPlugins.onAssembly(new ObservableTakeUntil(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> takeUntil(Predicate<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "predicate is null");
      return RxJavaPlugins.onAssembly(new ObservableTakeUntilPredicate(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> takeWhile(Predicate<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "predicate is null");
      return RxJavaPlugins.onAssembly(new ObservableTakeWhile(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final TestObserver<T> test() {
      TestObserver var1 = new TestObserver();
      this.subscribe((Observer)var1);
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final TestObserver<T> test(boolean var1) {
      TestObserver var2 = new TestObserver();
      if(var1) {
         var2.dispose();
      }

      this.subscribe((Observer)var2);
      return var2;
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Observable<T> throttleFirst(long var1, TimeUnit var3) {
      return this.throttleFirst(var1, var3, Schedulers.computation());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<T> throttleFirst(long var1, TimeUnit var3, Scheduler var4) {
      ObjectHelper.requireNonNull(var3, "unit is null");
      ObjectHelper.requireNonNull(var4, "scheduler is null");
      return RxJavaPlugins.onAssembly(new ObservableThrottleFirstTimed(this, var1, var3, var4));
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Observable<T> throttleLast(long var1, TimeUnit var3) {
      return this.sample(var1, var3);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<T> throttleLast(long var1, TimeUnit var3, Scheduler var4) {
      return this.sample(var1, var3, var4);
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Observable<T> throttleWithTimeout(long var1, TimeUnit var3) {
      return this.debounce(var1, var3);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<T> throttleWithTimeout(long var1, TimeUnit var3, Scheduler var4) {
      return this.debounce(var1, var3, var4);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<Timed<T>> timeInterval() {
      return this.timeInterval(TimeUnit.MILLISECONDS, Schedulers.computation());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<Timed<T>> timeInterval(Scheduler var1) {
      return this.timeInterval(TimeUnit.MILLISECONDS, var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<Timed<T>> timeInterval(TimeUnit var1) {
      return this.timeInterval(var1, Schedulers.computation());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<Timed<T>> timeInterval(TimeUnit var1, Scheduler var2) {
      ObjectHelper.requireNonNull(var1, "unit is null");
      ObjectHelper.requireNonNull(var2, "scheduler is null");
      return RxJavaPlugins.onAssembly(new ObservableTimeInterval(this, var1, var2));
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Observable<T> timeout(long var1, TimeUnit var3) {
      return this.timeout0(var1, var3, (ObservableSource)null, Schedulers.computation());
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Observable<T> timeout(long var1, TimeUnit var3, ObservableSource<? extends T> var4) {
      ObjectHelper.requireNonNull(var4, "other is null");
      return this.timeout0(var1, var3, var4, Schedulers.computation());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<T> timeout(long var1, TimeUnit var3, Scheduler var4) {
      return this.timeout0(var1, var3, (ObservableSource)null, var4);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<T> timeout(long var1, TimeUnit var3, Scheduler var4, ObservableSource<? extends T> var5) {
      ObjectHelper.requireNonNull(var5, "other is null");
      return this.timeout0(var1, var3, var5, var4);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, V extends Object> Observable<T> timeout(ObservableSource<U> var1, Function<? super T, ? extends ObservableSource<V>> var2) {
      ObjectHelper.requireNonNull(var1, "firstTimeoutIndicator is null");
      return this.timeout0(var1, var2, (ObservableSource)null);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, V extends Object> Observable<T> timeout(ObservableSource<U> var1, Function<? super T, ? extends ObservableSource<V>> var2, ObservableSource<? extends T> var3) {
      ObjectHelper.requireNonNull(var1, "firstTimeoutIndicator is null");
      ObjectHelper.requireNonNull(var3, "other is null");
      return this.timeout0(var1, var2, var3);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <V extends Object> Observable<T> timeout(Function<? super T, ? extends ObservableSource<V>> var1) {
      return this.timeout0((ObservableSource)null, var1, (ObservableSource)null);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <V extends Object> Observable<T> timeout(Function<? super T, ? extends ObservableSource<V>> var1, ObservableSource<? extends T> var2) {
      ObjectHelper.requireNonNull(var2, "other is null");
      return this.timeout0((ObservableSource)null, var1, var2);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<Timed<T>> timestamp() {
      return this.timestamp(TimeUnit.MILLISECONDS, Schedulers.computation());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<Timed<T>> timestamp(Scheduler var1) {
      return this.timestamp(TimeUnit.MILLISECONDS, var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<Timed<T>> timestamp(TimeUnit var1) {
      return this.timestamp(var1, Schedulers.computation());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<Timed<T>> timestamp(TimeUnit var1, Scheduler var2) {
      ObjectHelper.requireNonNull(var1, "unit is null");
      ObjectHelper.requireNonNull(var2, "scheduler is null");
      return this.map(Functions.timestampWith(var1, var2));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> R to(Function<? super Observable<T>, R> var1) {
      try {
         Object var3 = ((Function)ObjectHelper.requireNonNull(var1, "converter is null")).apply(this);
         return var3;
      } catch (Throwable var2) {
         Exceptions.throwIfFatal(var2);
         throw ExceptionHelper.wrapOrThrow(var2);
      }
   }

   @BackpressureSupport(BackpressureKind.SPECIAL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> toFlowable(BackpressureStrategy var1) {
      FlowableFromObservable var2 = new FlowableFromObservable(this);
      switch(1.$SwitchMap$io$reactivex$BackpressureStrategy[var1.ordinal()]) {
      case 1:
         return var2.onBackpressureDrop();
      case 2:
         return var2.onBackpressureLatest();
      case 3:
         return var2;
      case 4:
         return RxJavaPlugins.onAssembly(new FlowableOnBackpressureError(var2));
      default:
         return var2.onBackpressureBuffer();
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Future<T> toFuture() {
      return (Future)this.subscribeWith(new FutureObserver());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<List<T>> toList() {
      return this.toList(16);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<List<T>> toList(int var1) {
      ObjectHelper.verifyPositive(var1, "capacityHint");
      return RxJavaPlugins.onAssembly(new ObservableToListSingle(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object & Collection<? super T>> Single<U> toList(Callable<U> var1) {
      ObjectHelper.requireNonNull(var1, "collectionSupplier is null");
      return RxJavaPlugins.onAssembly(new ObservableToListSingle(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <K extends Object> Single<Map<K, T>> toMap(Function<? super T, ? extends K> var1) {
      ObjectHelper.requireNonNull(var1, "keySelector is null");
      return this.collect(HashMapSupplier.asCallable(), Functions.toMapKeySelector(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <K extends Object, V extends Object> Single<Map<K, V>> toMap(Function<? super T, ? extends K> var1, Function<? super T, ? extends V> var2) {
      ObjectHelper.requireNonNull(var1, "keySelector is null");
      ObjectHelper.requireNonNull(var2, "valueSelector is null");
      return this.collect(HashMapSupplier.asCallable(), Functions.toMapKeyValueSelector(var1, var2));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <K extends Object, V extends Object> Single<Map<K, V>> toMap(Function<? super T, ? extends K> var1, Function<? super T, ? extends V> var2, Callable<? extends Map<K, V>> var3) {
      ObjectHelper.requireNonNull(var1, "keySelector is null");
      ObjectHelper.requireNonNull(var2, "valueSelector is null");
      ObjectHelper.requireNonNull(var3, "mapSupplier is null");
      return this.collect(var3, Functions.toMapKeyValueSelector(var1, var2));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <K extends Object> Single<Map<K, Collection<T>>> toMultimap(Function<? super T, ? extends K> var1) {
      return this.toMultimap(var1, Functions.identity(), HashMapSupplier.asCallable(), ArrayListSupplier.asFunction());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <K extends Object, V extends Object> Single<Map<K, Collection<V>>> toMultimap(Function<? super T, ? extends K> var1, Function<? super T, ? extends V> var2) {
      return this.toMultimap(var1, var2, HashMapSupplier.asCallable(), ArrayListSupplier.asFunction());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <K extends Object, V extends Object> Single<Map<K, Collection<V>>> toMultimap(Function<? super T, ? extends K> var1, Function<? super T, ? extends V> var2, Callable<Map<K, Collection<V>>> var3) {
      return this.toMultimap(var1, var2, var3, ArrayListSupplier.asFunction());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <K extends Object, V extends Object> Single<Map<K, Collection<V>>> toMultimap(Function<? super T, ? extends K> var1, Function<? super T, ? extends V> var2, Callable<? extends Map<K, Collection<V>>> var3, Function<? super K, ? extends Collection<? super V>> var4) {
      ObjectHelper.requireNonNull(var1, "keySelector is null");
      ObjectHelper.requireNonNull(var2, "valueSelector is null");
      ObjectHelper.requireNonNull(var3, "mapSupplier is null");
      ObjectHelper.requireNonNull(var4, "collectionFactory is null");
      return this.collect(var3, Functions.toMultimapKeyValueSelector(var1, var2, var4));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<List<T>> toSortedList() {
      return this.toSortedList(Functions.naturalOrder());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<List<T>> toSortedList(int var1) {
      return this.toSortedList(Functions.naturalOrder(), var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<List<T>> toSortedList(Comparator<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "comparator is null");
      return this.toList().map(Functions.listSorter(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<List<T>> toSortedList(Comparator<? super T> var1, int var2) {
      ObjectHelper.requireNonNull(var1, "comparator is null");
      return this.toList(var2).map(Functions.listSorter(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<T> unsubscribeOn(Scheduler var1) {
      ObjectHelper.requireNonNull(var1, "scheduler is null");
      return RxJavaPlugins.onAssembly(new ObservableUnsubscribeOn(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<Observable<T>> window(long var1) {
      return this.window(var1, var1, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<Observable<T>> window(long var1, long var3) {
      return this.window(var1, var3, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<Observable<T>> window(long var1, long var3, int var5) {
      ObjectHelper.verifyPositive(var1, "count");
      ObjectHelper.verifyPositive(var3, "skip");
      ObjectHelper.verifyPositive(var5, "bufferSize");
      return RxJavaPlugins.onAssembly(new ObservableWindow(this, var1, var3, var5));
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Observable<Observable<T>> window(long var1, long var3, TimeUnit var5) {
      return this.window(var1, var3, var5, Schedulers.computation(), bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<Observable<T>> window(long var1, long var3, TimeUnit var5, Scheduler var6) {
      return this.window(var1, var3, var5, var6, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<Observable<T>> window(long var1, long var3, TimeUnit var5, Scheduler var6, int var7) {
      ObjectHelper.verifyPositive(var1, "timespan");
      ObjectHelper.verifyPositive(var3, "timeskip");
      ObjectHelper.verifyPositive(var7, "bufferSize");
      ObjectHelper.requireNonNull(var6, "scheduler is null");
      ObjectHelper.requireNonNull(var5, "unit is null");
      return RxJavaPlugins.onAssembly(new ObservableWindowTimed(this, var1, var3, var5, var6, Long.MAX_VALUE, var7, false));
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Observable<Observable<T>> window(long var1, TimeUnit var3) {
      return this.window(var1, var3, Schedulers.computation(), Long.MAX_VALUE, false);
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Observable<Observable<T>> window(long var1, TimeUnit var3, long var4) {
      return this.window(var1, var3, Schedulers.computation(), var4, false);
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Observable<Observable<T>> window(long var1, TimeUnit var3, long var4, boolean var6) {
      return this.window(var1, var3, Schedulers.computation(), var4, var6);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<Observable<T>> window(long var1, TimeUnit var3, Scheduler var4) {
      return this.window(var1, var3, var4, Long.MAX_VALUE, false);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<Observable<T>> window(long var1, TimeUnit var3, Scheduler var4, long var5) {
      return this.window(var1, var3, var4, var5, false);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<Observable<T>> window(long var1, TimeUnit var3, Scheduler var4, long var5, boolean var7) {
      return this.window(var1, var3, var4, var5, var7, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<Observable<T>> window(long var1, TimeUnit var3, Scheduler var4, long var5, boolean var7, int var8) {
      ObjectHelper.verifyPositive(var8, "bufferSize");
      ObjectHelper.requireNonNull(var4, "scheduler is null");
      ObjectHelper.requireNonNull(var3, "unit is null");
      ObjectHelper.verifyPositive(var5, "count");
      return RxJavaPlugins.onAssembly(new ObservableWindowTimed(this, var1, var1, var3, var4, var5, var8, var7));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <B extends Object> Observable<Observable<T>> window(ObservableSource<B> var1) {
      return this.window(var1, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <B extends Object> Observable<Observable<T>> window(ObservableSource<B> var1, int var2) {
      ObjectHelper.requireNonNull(var1, "boundary is null");
      ObjectHelper.verifyPositive(var2, "bufferSize");
      return RxJavaPlugins.onAssembly(new ObservableWindowBoundary(this, var1, var2));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, V extends Object> Observable<Observable<T>> window(ObservableSource<U> var1, Function<? super U, ? extends ObservableSource<V>> var2) {
      return this.window(var1, var2, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, V extends Object> Observable<Observable<T>> window(ObservableSource<U> var1, Function<? super U, ? extends ObservableSource<V>> var2, int var3) {
      ObjectHelper.requireNonNull(var1, "openingIndicator is null");
      ObjectHelper.requireNonNull(var2, "closingIndicator is null");
      ObjectHelper.verifyPositive(var3, "bufferSize");
      return RxJavaPlugins.onAssembly(new ObservableWindowBoundarySelector(this, var1, var2, var3));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <B extends Object> Observable<Observable<T>> window(Callable<? extends ObservableSource<B>> var1) {
      return this.window(var1, bufferSize());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <B extends Object> Observable<Observable<T>> window(Callable<? extends ObservableSource<B>> var1, int var2) {
      ObjectHelper.requireNonNull(var1, "boundary is null");
      ObjectHelper.verifyPositive(var2, "bufferSize");
      return RxJavaPlugins.onAssembly(new ObservableWindowBoundarySupplier(this, var1, var2));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, R extends Object> Observable<R> withLatestFrom(ObservableSource<T1> var1, ObservableSource<T2> var2, ObservableSource<T3> var3, ObservableSource<T4> var4, Function5<? super T, ? super T1, ? super T2, ? super T3, ? super T4, R> var5) {
      ObjectHelper.requireNonNull(var1, "o1 is null");
      ObjectHelper.requireNonNull(var2, "o2 is null");
      ObjectHelper.requireNonNull(var3, "o3 is null");
      ObjectHelper.requireNonNull(var4, "o4 is null");
      ObjectHelper.requireNonNull(var5, "combiner is null");
      Function var6 = Functions.toFunction(var5);
      return this.withLatestFrom(new ObservableSource[]{var1, var2, var3, var4}, var6);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T1 extends Object, T2 extends Object, T3 extends Object, R extends Object> Observable<R> withLatestFrom(ObservableSource<T1> var1, ObservableSource<T2> var2, ObservableSource<T3> var3, Function4<? super T, ? super T1, ? super T2, ? super T3, R> var4) {
      ObjectHelper.requireNonNull(var1, "o1 is null");
      ObjectHelper.requireNonNull(var2, "o2 is null");
      ObjectHelper.requireNonNull(var3, "o3 is null");
      ObjectHelper.requireNonNull(var4, "combiner is null");
      Function var5 = Functions.toFunction(var4);
      return this.withLatestFrom(new ObservableSource[]{var1, var2, var3}, var5);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T1 extends Object, T2 extends Object, R extends Object> Observable<R> withLatestFrom(ObservableSource<T1> var1, ObservableSource<T2> var2, Function3<? super T, ? super T1, ? super T2, R> var3) {
      ObjectHelper.requireNonNull(var1, "o1 is null");
      ObjectHelper.requireNonNull(var2, "o2 is null");
      ObjectHelper.requireNonNull(var3, "combiner is null");
      Function var4 = Functions.toFunction(var3);
      return this.withLatestFrom(new ObservableSource[]{var1, var2}, var4);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, R extends Object> Observable<R> withLatestFrom(ObservableSource<? extends U> var1, BiFunction<? super T, ? super U, ? extends R> var2) {
      ObjectHelper.requireNonNull(var1, "other is null");
      ObjectHelper.requireNonNull(var2, "combiner is null");
      return RxJavaPlugins.onAssembly(new ObservableWithLatestFrom(this, var2, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> withLatestFrom(Iterable<? extends ObservableSource<?>> var1, Function<? super Object[], R> var2) {
      ObjectHelper.requireNonNull(var1, "others is null");
      ObjectHelper.requireNonNull(var2, "combiner is null");
      return RxJavaPlugins.onAssembly(new ObservableWithLatestFromMany(this, var1, var2));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Observable<R> withLatestFrom(ObservableSource<?>[] var1, Function<? super Object[], R> var2) {
      ObjectHelper.requireNonNull(var1, "others is null");
      ObjectHelper.requireNonNull(var2, "combiner is null");
      return RxJavaPlugins.onAssembly(new ObservableWithLatestFromMany(this, var1, var2));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, R extends Object> Observable<R> zipWith(ObservableSource<? extends U> var1, BiFunction<? super T, ? super U, ? extends R> var2) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return zip(this, var1, var2);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, R extends Object> Observable<R> zipWith(ObservableSource<? extends U> var1, BiFunction<? super T, ? super U, ? extends R> var2, boolean var3) {
      return zip(this, var1, var2, var3);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, R extends Object> Observable<R> zipWith(ObservableSource<? extends U> var1, BiFunction<? super T, ? super U, ? extends R> var2, boolean var3, int var4) {
      return zip(this, var1, var2, var3, var4);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, R extends Object> Observable<R> zipWith(Iterable<U> var1, BiFunction<? super T, ? super U, ? extends R> var2) {
      ObjectHelper.requireNonNull(var1, "other is null");
      ObjectHelper.requireNonNull(var2, "zipper is null");
      return RxJavaPlugins.onAssembly(new ObservableZipIterable(this, var1, var2));
   }
}
