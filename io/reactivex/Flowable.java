package io.reactivex;

import io.reactivex.BackpressureOverflowStrategy;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Emitter;
import io.reactivex.FlowableConverter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableOperator;
import io.reactivex.FlowableSubscriber;
import io.reactivex.FlowableTransformer;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.BackpressureKind;
import io.reactivex.annotations.BackpressureSupport;
import io.reactivex.annotations.Beta;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.Experimental;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.flowables.GroupedFlowable;
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
import io.reactivex.functions.LongConsumer;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.ScalarCallable;
import io.reactivex.internal.operators.flowable.BlockingFlowableIterable;
import io.reactivex.internal.operators.flowable.BlockingFlowableLatest;
import io.reactivex.internal.operators.flowable.BlockingFlowableMostRecent;
import io.reactivex.internal.operators.flowable.BlockingFlowableNext;
import io.reactivex.internal.operators.flowable.FlowableAllSingle;
import io.reactivex.internal.operators.flowable.FlowableAmb;
import io.reactivex.internal.operators.flowable.FlowableAnySingle;
import io.reactivex.internal.operators.flowable.FlowableBlockingSubscribe;
import io.reactivex.internal.operators.flowable.FlowableBuffer;
import io.reactivex.internal.operators.flowable.FlowableBufferBoundary;
import io.reactivex.internal.operators.flowable.FlowableBufferBoundarySupplier;
import io.reactivex.internal.operators.flowable.FlowableBufferExactBoundary;
import io.reactivex.internal.operators.flowable.FlowableBufferTimed;
import io.reactivex.internal.operators.flowable.FlowableCache;
import io.reactivex.internal.operators.flowable.FlowableCollectSingle;
import io.reactivex.internal.operators.flowable.FlowableCombineLatest;
import io.reactivex.internal.operators.flowable.FlowableConcatArray;
import io.reactivex.internal.operators.flowable.FlowableConcatMap;
import io.reactivex.internal.operators.flowable.FlowableConcatMapEager;
import io.reactivex.internal.operators.flowable.FlowableConcatMapEagerPublisher;
import io.reactivex.internal.operators.flowable.FlowableCountSingle;
import io.reactivex.internal.operators.flowable.FlowableCreate;
import io.reactivex.internal.operators.flowable.FlowableDebounce;
import io.reactivex.internal.operators.flowable.FlowableDebounceTimed;
import io.reactivex.internal.operators.flowable.FlowableDefer;
import io.reactivex.internal.operators.flowable.FlowableDelay;
import io.reactivex.internal.operators.flowable.FlowableDelaySubscriptionOther;
import io.reactivex.internal.operators.flowable.FlowableDematerialize;
import io.reactivex.internal.operators.flowable.FlowableDetach;
import io.reactivex.internal.operators.flowable.FlowableDistinct;
import io.reactivex.internal.operators.flowable.FlowableDistinctUntilChanged;
import io.reactivex.internal.operators.flowable.FlowableDoAfterNext;
import io.reactivex.internal.operators.flowable.FlowableDoFinally;
import io.reactivex.internal.operators.flowable.FlowableDoOnEach;
import io.reactivex.internal.operators.flowable.FlowableDoOnLifecycle;
import io.reactivex.internal.operators.flowable.FlowableElementAtMaybe;
import io.reactivex.internal.operators.flowable.FlowableElementAtSingle;
import io.reactivex.internal.operators.flowable.FlowableEmpty;
import io.reactivex.internal.operators.flowable.FlowableError;
import io.reactivex.internal.operators.flowable.FlowableFilter;
import io.reactivex.internal.operators.flowable.FlowableFlatMap;
import io.reactivex.internal.operators.flowable.FlowableFlatMapCompletableCompletable;
import io.reactivex.internal.operators.flowable.FlowableFlatMapMaybe;
import io.reactivex.internal.operators.flowable.FlowableFlatMapSingle;
import io.reactivex.internal.operators.flowable.FlowableFlattenIterable;
import io.reactivex.internal.operators.flowable.FlowableFromArray;
import io.reactivex.internal.operators.flowable.FlowableFromCallable;
import io.reactivex.internal.operators.flowable.FlowableFromFuture;
import io.reactivex.internal.operators.flowable.FlowableFromIterable;
import io.reactivex.internal.operators.flowable.FlowableFromPublisher;
import io.reactivex.internal.operators.flowable.FlowableGenerate;
import io.reactivex.internal.operators.flowable.FlowableGroupBy;
import io.reactivex.internal.operators.flowable.FlowableGroupJoin;
import io.reactivex.internal.operators.flowable.FlowableHide;
import io.reactivex.internal.operators.flowable.FlowableIgnoreElements;
import io.reactivex.internal.operators.flowable.FlowableIgnoreElementsCompletable;
import io.reactivex.internal.operators.flowable.FlowableInternalHelper;
import io.reactivex.internal.operators.flowable.FlowableInterval;
import io.reactivex.internal.operators.flowable.FlowableIntervalRange;
import io.reactivex.internal.operators.flowable.FlowableJoin;
import io.reactivex.internal.operators.flowable.FlowableJust;
import io.reactivex.internal.operators.flowable.FlowableLastMaybe;
import io.reactivex.internal.operators.flowable.FlowableLastSingle;
import io.reactivex.internal.operators.flowable.FlowableLift;
import io.reactivex.internal.operators.flowable.FlowableLimit;
import io.reactivex.internal.operators.flowable.FlowableMap;
import io.reactivex.internal.operators.flowable.FlowableMapNotification;
import io.reactivex.internal.operators.flowable.FlowableMaterialize;
import io.reactivex.internal.operators.flowable.FlowableNever;
import io.reactivex.internal.operators.flowable.FlowableObserveOn;
import io.reactivex.internal.operators.flowable.FlowableOnBackpressureBuffer;
import io.reactivex.internal.operators.flowable.FlowableOnBackpressureBufferStrategy;
import io.reactivex.internal.operators.flowable.FlowableOnBackpressureDrop;
import io.reactivex.internal.operators.flowable.FlowableOnBackpressureLatest;
import io.reactivex.internal.operators.flowable.FlowableOnErrorNext;
import io.reactivex.internal.operators.flowable.FlowableOnErrorReturn;
import io.reactivex.internal.operators.flowable.FlowablePublish;
import io.reactivex.internal.operators.flowable.FlowablePublishMulticast;
import io.reactivex.internal.operators.flowable.FlowableRange;
import io.reactivex.internal.operators.flowable.FlowableRangeLong;
import io.reactivex.internal.operators.flowable.FlowableReduceMaybe;
import io.reactivex.internal.operators.flowable.FlowableReduceSeedSingle;
import io.reactivex.internal.operators.flowable.FlowableReduceWithSingle;
import io.reactivex.internal.operators.flowable.FlowableRepeat;
import io.reactivex.internal.operators.flowable.FlowableRepeatUntil;
import io.reactivex.internal.operators.flowable.FlowableRepeatWhen;
import io.reactivex.internal.operators.flowable.FlowableReplay;
import io.reactivex.internal.operators.flowable.FlowableRetryBiPredicate;
import io.reactivex.internal.operators.flowable.FlowableRetryPredicate;
import io.reactivex.internal.operators.flowable.FlowableRetryWhen;
import io.reactivex.internal.operators.flowable.FlowableSamplePublisher;
import io.reactivex.internal.operators.flowable.FlowableSampleTimed;
import io.reactivex.internal.operators.flowable.FlowableScalarXMap;
import io.reactivex.internal.operators.flowable.FlowableScan;
import io.reactivex.internal.operators.flowable.FlowableScanSeed;
import io.reactivex.internal.operators.flowable.FlowableSequenceEqualSingle;
import io.reactivex.internal.operators.flowable.FlowableSerialized;
import io.reactivex.internal.operators.flowable.FlowableSingleMaybe;
import io.reactivex.internal.operators.flowable.FlowableSingleSingle;
import io.reactivex.internal.operators.flowable.FlowableSkip;
import io.reactivex.internal.operators.flowable.FlowableSkipLast;
import io.reactivex.internal.operators.flowable.FlowableSkipLastTimed;
import io.reactivex.internal.operators.flowable.FlowableSkipUntil;
import io.reactivex.internal.operators.flowable.FlowableSkipWhile;
import io.reactivex.internal.operators.flowable.FlowableSubscribeOn;
import io.reactivex.internal.operators.flowable.FlowableSwitchIfEmpty;
import io.reactivex.internal.operators.flowable.FlowableSwitchMap;
import io.reactivex.internal.operators.flowable.FlowableTake;
import io.reactivex.internal.operators.flowable.FlowableTakeLast;
import io.reactivex.internal.operators.flowable.FlowableTakeLastOne;
import io.reactivex.internal.operators.flowable.FlowableTakeLastTimed;
import io.reactivex.internal.operators.flowable.FlowableTakeUntil;
import io.reactivex.internal.operators.flowable.FlowableTakeUntilPredicate;
import io.reactivex.internal.operators.flowable.FlowableTakeWhile;
import io.reactivex.internal.operators.flowable.FlowableThrottleFirstTimed;
import io.reactivex.internal.operators.flowable.FlowableTimeInterval;
import io.reactivex.internal.operators.flowable.FlowableTimeout;
import io.reactivex.internal.operators.flowable.FlowableTimeoutTimed;
import io.reactivex.internal.operators.flowable.FlowableTimer;
import io.reactivex.internal.operators.flowable.FlowableToListSingle;
import io.reactivex.internal.operators.flowable.FlowableUnsubscribeOn;
import io.reactivex.internal.operators.flowable.FlowableUsing;
import io.reactivex.internal.operators.flowable.FlowableWindow;
import io.reactivex.internal.operators.flowable.FlowableWindowBoundary;
import io.reactivex.internal.operators.flowable.FlowableWindowBoundarySelector;
import io.reactivex.internal.operators.flowable.FlowableWindowBoundarySupplier;
import io.reactivex.internal.operators.flowable.FlowableWindowTimed;
import io.reactivex.internal.operators.flowable.FlowableWithLatestFrom;
import io.reactivex.internal.operators.flowable.FlowableWithLatestFromMany;
import io.reactivex.internal.operators.flowable.FlowableZip;
import io.reactivex.internal.operators.flowable.FlowableZipIterable;
import io.reactivex.internal.operators.flowable.FlowableInternalHelper.RequestMax;
import io.reactivex.internal.operators.observable.ObservableFromPublisher;
import io.reactivex.internal.schedulers.ImmediateThinScheduler;
import io.reactivex.internal.subscribers.BlockingFirstSubscriber;
import io.reactivex.internal.subscribers.BlockingLastSubscriber;
import io.reactivex.internal.subscribers.ForEachWhileSubscriber;
import io.reactivex.internal.subscribers.FutureSubscriber;
import io.reactivex.internal.subscribers.LambdaSubscriber;
import io.reactivex.internal.subscribers.StrictSubscriber;
import io.reactivex.internal.util.ArrayListSupplier;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.HashMapSupplier;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;
import io.reactivex.subscribers.SafeSubscriber;
import io.reactivex.subscribers.TestSubscriber;
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
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public abstract class Flowable<T extends Object> implements Publisher<T> {

   static final int BUFFER_SIZE = Math.max(1, Integer.getInteger("rx2.buffer-size", 128).intValue());


   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> amb(Iterable<? extends Publisher<? extends T>> var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      return RxJavaPlugins.onAssembly(new FlowableAmb((Publisher[])null, var0));
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> ambArray(Publisher<? extends T> ... var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      int var1 = var0.length;
      return var1 == 0?empty():(var1 == 1?fromPublisher(var0[0]):RxJavaPlugins.onAssembly(new FlowableAmb(var0, (Iterable)null)));
   }

   public static int bufferSize() {
      return BUFFER_SIZE;
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Flowable<R> combineLatest(Function<? super Object[], ? extends R> var0, Publisher<? extends T> ... var1) {
      return combineLatest(var1, var0, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Flowable<R> combineLatest(Iterable<? extends Publisher<? extends T>> var0, Function<? super Object[], ? extends R> var1) {
      return combineLatest(var0, var1, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Flowable<R> combineLatest(Iterable<? extends Publisher<? extends T>> var0, Function<? super Object[], ? extends R> var1, int var2) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      ObjectHelper.requireNonNull(var1, "combiner is null");
      ObjectHelper.verifyPositive(var2, "bufferSize");
      return RxJavaPlugins.onAssembly(new FlowableCombineLatest(var0, var1, var2, false));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, R extends Object> Flowable<R> combineLatest(Publisher<? extends T1> var0, Publisher<? extends T2> var1, BiFunction<? super T1, ? super T2, ? extends R> var2) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      return combineLatest(Functions.toFunction(var2), new Publisher[]{var0, var1});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, R extends Object> Flowable<R> combineLatest(Publisher<? extends T1> var0, Publisher<? extends T2> var1, Publisher<? extends T3> var2, Function3<? super T1, ? super T2, ? super T3, ? extends R> var3) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      return combineLatest(Functions.toFunction(var3), new Publisher[]{var0, var1, var2});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, R extends Object> Flowable<R> combineLatest(Publisher<? extends T1> var0, Publisher<? extends T2> var1, Publisher<? extends T3> var2, Publisher<? extends T4> var3, Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> var4) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      return combineLatest(Functions.toFunction(var4), new Publisher[]{var0, var1, var2, var3});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, T5 extends Object, R extends Object> Flowable<R> combineLatest(Publisher<? extends T1> var0, Publisher<? extends T2> var1, Publisher<? extends T3> var2, Publisher<? extends T4> var3, Publisher<? extends T5> var4, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> var5) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      ObjectHelper.requireNonNull(var4, "source5 is null");
      return combineLatest(Functions.toFunction(var5), new Publisher[]{var0, var1, var2, var3, var4});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, T5 extends Object, T6 extends Object, R extends Object> Flowable<R> combineLatest(Publisher<? extends T1> var0, Publisher<? extends T2> var1, Publisher<? extends T3> var2, Publisher<? extends T4> var3, Publisher<? extends T5> var4, Publisher<? extends T6> var5, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> var6) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      ObjectHelper.requireNonNull(var4, "source5 is null");
      ObjectHelper.requireNonNull(var5, "source6 is null");
      return combineLatest(Functions.toFunction(var6), new Publisher[]{var0, var1, var2, var3, var4, var5});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, T5 extends Object, T6 extends Object, T7 extends Object, R extends Object> Flowable<R> combineLatest(Publisher<? extends T1> var0, Publisher<? extends T2> var1, Publisher<? extends T3> var2, Publisher<? extends T4> var3, Publisher<? extends T5> var4, Publisher<? extends T6> var5, Publisher<? extends T7> var6, Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> var7) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      ObjectHelper.requireNonNull(var4, "source5 is null");
      ObjectHelper.requireNonNull(var5, "source6 is null");
      ObjectHelper.requireNonNull(var6, "source7 is null");
      return combineLatest(Functions.toFunction(var7), new Publisher[]{var0, var1, var2, var3, var4, var5, var6});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, T5 extends Object, T6 extends Object, T7 extends Object, T8 extends Object, R extends Object> Flowable<R> combineLatest(Publisher<? extends T1> var0, Publisher<? extends T2> var1, Publisher<? extends T3> var2, Publisher<? extends T4> var3, Publisher<? extends T5> var4, Publisher<? extends T6> var5, Publisher<? extends T7> var6, Publisher<? extends T8> var7, Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> var8) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      ObjectHelper.requireNonNull(var4, "source5 is null");
      ObjectHelper.requireNonNull(var5, "source6 is null");
      ObjectHelper.requireNonNull(var6, "source7 is null");
      ObjectHelper.requireNonNull(var7, "source8 is null");
      return combineLatest(Functions.toFunction(var8), new Publisher[]{var0, var1, var2, var3, var4, var5, var6, var7});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, T5 extends Object, T6 extends Object, T7 extends Object, T8 extends Object, T9 extends Object, R extends Object> Flowable<R> combineLatest(Publisher<? extends T1> var0, Publisher<? extends T2> var1, Publisher<? extends T3> var2, Publisher<? extends T4> var3, Publisher<? extends T5> var4, Publisher<? extends T6> var5, Publisher<? extends T7> var6, Publisher<? extends T8> var7, Publisher<? extends T9> var8, Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> var9) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      ObjectHelper.requireNonNull(var4, "source5 is null");
      ObjectHelper.requireNonNull(var5, "source6 is null");
      ObjectHelper.requireNonNull(var6, "source7 is null");
      ObjectHelper.requireNonNull(var7, "source8 is null");
      ObjectHelper.requireNonNull(var8, "source9 is null");
      return combineLatest(Functions.toFunction(var9), new Publisher[]{var0, var1, var2, var3, var4, var5, var6, var7, var8});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Flowable<R> combineLatest(Publisher<? extends T>[] var0, Function<? super Object[], ? extends R> var1) {
      return combineLatest(var0, var1, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Flowable<R> combineLatest(Publisher<? extends T>[] var0, Function<? super Object[], ? extends R> var1, int var2) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      if(var0.length == 0) {
         return empty();
      } else {
         ObjectHelper.requireNonNull(var1, "combiner is null");
         ObjectHelper.verifyPositive(var2, "bufferSize");
         return RxJavaPlugins.onAssembly(new FlowableCombineLatest(var0, var1, var2, false));
      }
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Flowable<R> combineLatestDelayError(Function<? super Object[], ? extends R> var0, int var1, Publisher<? extends T> ... var2) {
      return combineLatestDelayError(var2, var0, var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Flowable<R> combineLatestDelayError(Function<? super Object[], ? extends R> var0, Publisher<? extends T> ... var1) {
      return combineLatestDelayError(var1, var0, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Flowable<R> combineLatestDelayError(Iterable<? extends Publisher<? extends T>> var0, Function<? super Object[], ? extends R> var1) {
      return combineLatestDelayError(var0, var1, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Flowable<R> combineLatestDelayError(Iterable<? extends Publisher<? extends T>> var0, Function<? super Object[], ? extends R> var1, int var2) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      ObjectHelper.requireNonNull(var1, "combiner is null");
      ObjectHelper.verifyPositive(var2, "bufferSize");
      return RxJavaPlugins.onAssembly(new FlowableCombineLatest(var0, var1, var2, true));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Flowable<R> combineLatestDelayError(Publisher<? extends T>[] var0, Function<? super Object[], ? extends R> var1) {
      return combineLatestDelayError(var0, var1, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Flowable<R> combineLatestDelayError(Publisher<? extends T>[] var0, Function<? super Object[], ? extends R> var1, int var2) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      ObjectHelper.requireNonNull(var1, "combiner is null");
      ObjectHelper.verifyPositive(var2, "bufferSize");
      return var0.length == 0?empty():RxJavaPlugins.onAssembly(new FlowableCombineLatest(var0, var1, var2, true));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> concat(Iterable<? extends Publisher<? extends T>> var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      return fromIterable(var0).concatMapDelayError(Functions.identity(), 2, false);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> concat(Publisher<? extends Publisher<? extends T>> var0) {
      return concat(var0, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> concat(Publisher<? extends Publisher<? extends T>> var0, int var1) {
      return fromPublisher(var0).concatMap(Functions.identity(), var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> concat(Publisher<? extends T> var0, Publisher<? extends T> var1) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      return concatArray(new Publisher[]{var0, var1});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> concat(Publisher<? extends T> var0, Publisher<? extends T> var1, Publisher<? extends T> var2) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      return concatArray(new Publisher[]{var0, var1, var2});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> concat(Publisher<? extends T> var0, Publisher<? extends T> var1, Publisher<? extends T> var2, Publisher<? extends T> var3) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      return concatArray(new Publisher[]{var0, var1, var2, var3});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> concatArray(Publisher<? extends T> ... var0) {
      return var0.length == 0?empty():(var0.length == 1?fromPublisher(var0[0]):RxJavaPlugins.onAssembly(new FlowableConcatArray(var0, false)));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> concatArrayDelayError(Publisher<? extends T> ... var0) {
      return var0.length == 0?empty():(var0.length == 1?fromPublisher(var0[0]):RxJavaPlugins.onAssembly(new FlowableConcatArray(var0, true)));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> concatArrayEager(int var0, int var1, Publisher<? extends T> ... var2) {
      ObjectHelper.requireNonNull(var2, "sources is null");
      ObjectHelper.verifyPositive(var0, "maxConcurrency");
      ObjectHelper.verifyPositive(var1, "prefetch");
      return RxJavaPlugins.onAssembly(new FlowableConcatMapEager(new FlowableFromArray(var2), Functions.identity(), var0, var1, ErrorMode.IMMEDIATE));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> concatArrayEager(Publisher<? extends T> ... var0) {
      return concatArrayEager(bufferSize(), bufferSize(), var0);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> concatDelayError(Iterable<? extends Publisher<? extends T>> var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      return fromIterable(var0).concatMapDelayError(Functions.identity());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> concatDelayError(Publisher<? extends Publisher<? extends T>> var0) {
      return concatDelayError(var0, bufferSize(), true);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> concatDelayError(Publisher<? extends Publisher<? extends T>> var0, int var1, boolean var2) {
      return fromPublisher(var0).concatMapDelayError(Functions.identity(), var1, var2);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> concatEager(Iterable<? extends Publisher<? extends T>> var0) {
      return concatEager(var0, bufferSize(), bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> concatEager(Iterable<? extends Publisher<? extends T>> var0, int var1, int var2) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      ObjectHelper.verifyPositive(var1, "maxConcurrency");
      ObjectHelper.verifyPositive(var2, "prefetch");
      return RxJavaPlugins.onAssembly(new FlowableConcatMapEager(new FlowableFromIterable(var0), Functions.identity(), var1, var2, ErrorMode.IMMEDIATE));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> concatEager(Publisher<? extends Publisher<? extends T>> var0) {
      return concatEager(var0, bufferSize(), bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> concatEager(Publisher<? extends Publisher<? extends T>> var0, int var1, int var2) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      ObjectHelper.verifyPositive(var1, "maxConcurrency");
      ObjectHelper.verifyPositive(var2, "prefetch");
      return RxJavaPlugins.onAssembly(new FlowableConcatMapEagerPublisher(var0, Functions.identity(), var1, var2, ErrorMode.IMMEDIATE));
   }

   @BackpressureSupport(BackpressureKind.SPECIAL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> create(FlowableOnSubscribe<T> var0, BackpressureStrategy var1) {
      ObjectHelper.requireNonNull(var0, "source is null");
      ObjectHelper.requireNonNull(var1, "mode is null");
      return RxJavaPlugins.onAssembly(new FlowableCreate(var0, var1));
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> defer(Callable<? extends Publisher<? extends T>> var0) {
      ObjectHelper.requireNonNull(var0, "supplier is null");
      return RxJavaPlugins.onAssembly(new FlowableDefer(var0));
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   private Flowable<T> doOnEach(Consumer<? super T> var1, Consumer<? super Throwable> var2, Action var3, Action var4) {
      ObjectHelper.requireNonNull(var1, "onNext is null");
      ObjectHelper.requireNonNull(var2, "onError is null");
      ObjectHelper.requireNonNull(var3, "onComplete is null");
      ObjectHelper.requireNonNull(var4, "onAfterTerminate is null");
      return RxJavaPlugins.onAssembly(new FlowableDoOnEach(this, var1, var2, var3, var4));
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> empty() {
      return RxJavaPlugins.onAssembly(FlowableEmpty.INSTANCE);
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> error(Throwable var0) {
      ObjectHelper.requireNonNull(var0, "throwable is null");
      return error(Functions.justCallable(var0));
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> error(Callable<? extends Throwable> var0) {
      ObjectHelper.requireNonNull(var0, "errorSupplier is null");
      return RxJavaPlugins.onAssembly(new FlowableError(var0));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> fromArray(T ... var0) {
      ObjectHelper.requireNonNull(var0, "items is null");
      return var0.length == 0?empty():(var0.length == 1?just(var0[0]):RxJavaPlugins.onAssembly(new FlowableFromArray(var0)));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> fromCallable(Callable<? extends T> var0) {
      ObjectHelper.requireNonNull(var0, "supplier is null");
      return RxJavaPlugins.onAssembly(new FlowableFromCallable(var0));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> fromFuture(Future<? extends T> var0) {
      ObjectHelper.requireNonNull(var0, "future is null");
      return RxJavaPlugins.onAssembly(new FlowableFromFuture(var0, 0L, (TimeUnit)null));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> fromFuture(Future<? extends T> var0, long var1, TimeUnit var3) {
      ObjectHelper.requireNonNull(var0, "future is null");
      ObjectHelper.requireNonNull(var3, "unit is null");
      return RxJavaPlugins.onAssembly(new FlowableFromFuture(var0, var1, var3));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public static <T extends Object> Flowable<T> fromFuture(Future<? extends T> var0, long var1, TimeUnit var3, Scheduler var4) {
      ObjectHelper.requireNonNull(var4, "scheduler is null");
      return fromFuture(var0, var1, var3).subscribeOn(var4);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public static <T extends Object> Flowable<T> fromFuture(Future<? extends T> var0, Scheduler var1) {
      ObjectHelper.requireNonNull(var1, "scheduler is null");
      return fromFuture(var0).subscribeOn(var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> fromIterable(Iterable<? extends T> var0) {
      ObjectHelper.requireNonNull(var0, "source is null");
      return RxJavaPlugins.onAssembly(new FlowableFromIterable(var0));
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> fromPublisher(Publisher<? extends T> var0) {
      if(var0 instanceof Flowable) {
         return RxJavaPlugins.onAssembly((Flowable)var0);
      } else {
         ObjectHelper.requireNonNull(var0, "publisher is null");
         return RxJavaPlugins.onAssembly(new FlowableFromPublisher(var0));
      }
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> generate(Consumer<Emitter<T>> var0) {
      ObjectHelper.requireNonNull(var0, "generator is null");
      return generate(Functions.nullSupplier(), FlowableInternalHelper.simpleGenerator(var0), Functions.emptyConsumer());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, S extends Object> Flowable<T> generate(Callable<S> var0, BiConsumer<S, Emitter<T>> var1) {
      ObjectHelper.requireNonNull(var1, "generator is null");
      return generate(var0, FlowableInternalHelper.simpleBiGenerator(var1), Functions.emptyConsumer());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, S extends Object> Flowable<T> generate(Callable<S> var0, BiConsumer<S, Emitter<T>> var1, Consumer<? super S> var2) {
      ObjectHelper.requireNonNull(var1, "generator is null");
      return generate(var0, FlowableInternalHelper.simpleBiGenerator(var1), var2);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, S extends Object> Flowable<T> generate(Callable<S> var0, BiFunction<S, Emitter<T>, S> var1) {
      return generate(var0, var1, Functions.emptyConsumer());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, S extends Object> Flowable<T> generate(Callable<S> var0, BiFunction<S, Emitter<T>, S> var1, Consumer<? super S> var2) {
      ObjectHelper.requireNonNull(var0, "initialState is null");
      ObjectHelper.requireNonNull(var1, "generator is null");
      ObjectHelper.requireNonNull(var2, "disposeState is null");
      return RxJavaPlugins.onAssembly(new FlowableGenerate(var0, var1, var2));
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public static Flowable<Long> interval(long var0, long var2, TimeUnit var4) {
      return interval(var0, var2, var4, Schedulers.computation());
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public static Flowable<Long> interval(long var0, long var2, TimeUnit var4, Scheduler var5) {
      ObjectHelper.requireNonNull(var4, "unit is null");
      ObjectHelper.requireNonNull(var5, "scheduler is null");
      return RxJavaPlugins.onAssembly(new FlowableInterval(Math.max(0L, var0), Math.max(0L, var2), var4, var5));
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public static Flowable<Long> interval(long var0, TimeUnit var2) {
      return interval(var0, var0, var2, Schedulers.computation());
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public static Flowable<Long> interval(long var0, TimeUnit var2, Scheduler var3) {
      return interval(var0, var0, var2, var3);
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public static Flowable<Long> intervalRange(long var0, long var2, long var4, long var6, TimeUnit var8) {
      return intervalRange(var0, var2, var4, var6, var8, Schedulers.computation());
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public static Flowable<Long> intervalRange(long var0, long var2, long var4, long var6, TimeUnit var8, Scheduler var9) {
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
            return RxJavaPlugins.onAssembly(new FlowableIntervalRange(var0, var2, Math.max(0L, var4), Math.max(0L, var6), var8, var9));
         }
      }
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> just(T var0) {
      ObjectHelper.requireNonNull(var0, "item is null");
      return RxJavaPlugins.onAssembly(new FlowableJust(var0));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> just(T var0, T var1) {
      ObjectHelper.requireNonNull(var0, "The first item is null");
      ObjectHelper.requireNonNull(var1, "The second item is null");
      return fromArray(new Object[]{var0, var1});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> just(T var0, T var1, T var2) {
      ObjectHelper.requireNonNull(var0, "The first item is null");
      ObjectHelper.requireNonNull(var1, "The second item is null");
      ObjectHelper.requireNonNull(var2, "The third item is null");
      return fromArray(new Object[]{var0, var1, var2});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> just(T var0, T var1, T var2, T var3) {
      ObjectHelper.requireNonNull(var0, "The first item is null");
      ObjectHelper.requireNonNull(var1, "The second item is null");
      ObjectHelper.requireNonNull(var2, "The third item is null");
      ObjectHelper.requireNonNull(var3, "The fourth item is null");
      return fromArray(new Object[]{var0, var1, var2, var3});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> just(T var0, T var1, T var2, T var3, T var4) {
      ObjectHelper.requireNonNull(var0, "The first item is null");
      ObjectHelper.requireNonNull(var1, "The second item is null");
      ObjectHelper.requireNonNull(var2, "The third item is null");
      ObjectHelper.requireNonNull(var3, "The fourth item is null");
      ObjectHelper.requireNonNull(var4, "The fifth item is null");
      return fromArray(new Object[]{var0, var1, var2, var3, var4});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> just(T var0, T var1, T var2, T var3, T var4, T var5) {
      ObjectHelper.requireNonNull(var0, "The first item is null");
      ObjectHelper.requireNonNull(var1, "The second item is null");
      ObjectHelper.requireNonNull(var2, "The third item is null");
      ObjectHelper.requireNonNull(var3, "The fourth item is null");
      ObjectHelper.requireNonNull(var4, "The fifth item is null");
      ObjectHelper.requireNonNull(var5, "The sixth item is null");
      return fromArray(new Object[]{var0, var1, var2, var3, var4, var5});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> just(T var0, T var1, T var2, T var3, T var4, T var5, T var6) {
      ObjectHelper.requireNonNull(var0, "The first item is null");
      ObjectHelper.requireNonNull(var1, "The second item is null");
      ObjectHelper.requireNonNull(var2, "The third item is null");
      ObjectHelper.requireNonNull(var3, "The fourth item is null");
      ObjectHelper.requireNonNull(var4, "The fifth item is null");
      ObjectHelper.requireNonNull(var5, "The sixth item is null");
      ObjectHelper.requireNonNull(var6, "The seventh item is null");
      return fromArray(new Object[]{var0, var1, var2, var3, var4, var5, var6});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> just(T var0, T var1, T var2, T var3, T var4, T var5, T var6, T var7) {
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

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> just(T var0, T var1, T var2, T var3, T var4, T var5, T var6, T var7, T var8) {
      ObjectHelper.requireNonNull(var0, "The first item is null");
      ObjectHelper.requireNonNull(var1, "The second item is null");
      ObjectHelper.requireNonNull(var2, "The third item is null");
      ObjectHelper.requireNonNull(var3, "The fourth item is null");
      ObjectHelper.requireNonNull(var4, "The fifth item is null");
      ObjectHelper.requireNonNull(var5, "The sixth item is null");
      ObjectHelper.requireNonNull(var6, "The seventh item is null");
      ObjectHelper.requireNonNull(var7, "The eighth item is null");
      ObjectHelper.requireNonNull(var8, "The ninth is null");
      return fromArray(new Object[]{var0, var1, var2, var3, var4, var5, var6, var7, var8});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> just(T var0, T var1, T var2, T var3, T var4, T var5, T var6, T var7, T var8, T var9) {
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

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> merge(Iterable<? extends Publisher<? extends T>> var0) {
      return fromIterable(var0).flatMap(Functions.identity());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> merge(Iterable<? extends Publisher<? extends T>> var0, int var1) {
      return fromIterable(var0).flatMap(Functions.identity(), var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> merge(Iterable<? extends Publisher<? extends T>> var0, int var1, int var2) {
      return fromIterable(var0).flatMap(Functions.identity(), false, var1, var2);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> merge(Publisher<? extends Publisher<? extends T>> var0) {
      return merge(var0, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> merge(Publisher<? extends Publisher<? extends T>> var0, int var1) {
      return fromPublisher(var0).flatMap(Functions.identity(), var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> merge(Publisher<? extends T> var0, Publisher<? extends T> var1) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      return fromArray(new Publisher[]{var0, var1}).flatMap(Functions.identity(), false, 2);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> merge(Publisher<? extends T> var0, Publisher<? extends T> var1, Publisher<? extends T> var2) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      return fromArray(new Publisher[]{var0, var1, var2}).flatMap(Functions.identity(), false, 3);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> merge(Publisher<? extends T> var0, Publisher<? extends T> var1, Publisher<? extends T> var2, Publisher<? extends T> var3) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      return fromArray(new Publisher[]{var0, var1, var2, var3}).flatMap(Functions.identity(), false, 4);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> mergeArray(int var0, int var1, Publisher<? extends T> ... var2) {
      return fromArray(var2).flatMap(Functions.identity(), false, var0, var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> mergeArray(Publisher<? extends T> ... var0) {
      return fromArray(var0).flatMap(Functions.identity(), var0.length);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> mergeArrayDelayError(int var0, int var1, Publisher<? extends T> ... var2) {
      return fromArray(var2).flatMap(Functions.identity(), true, var0, var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> mergeArrayDelayError(Publisher<? extends T> ... var0) {
      return fromArray(var0).flatMap(Functions.identity(), true, var0.length);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> mergeDelayError(Iterable<? extends Publisher<? extends T>> var0) {
      return fromIterable(var0).flatMap(Functions.identity(), true);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> mergeDelayError(Iterable<? extends Publisher<? extends T>> var0, int var1) {
      return fromIterable(var0).flatMap(Functions.identity(), true, var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> mergeDelayError(Iterable<? extends Publisher<? extends T>> var0, int var1, int var2) {
      return fromIterable(var0).flatMap(Functions.identity(), true, var1, var2);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> mergeDelayError(Publisher<? extends Publisher<? extends T>> var0) {
      return mergeDelayError(var0, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> mergeDelayError(Publisher<? extends Publisher<? extends T>> var0, int var1) {
      return fromPublisher(var0).flatMap(Functions.identity(), true, var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> mergeDelayError(Publisher<? extends T> var0, Publisher<? extends T> var1) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      return fromArray(new Publisher[]{var0, var1}).flatMap(Functions.identity(), true, 2);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> mergeDelayError(Publisher<? extends T> var0, Publisher<? extends T> var1, Publisher<? extends T> var2) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      return fromArray(new Publisher[]{var0, var1, var2}).flatMap(Functions.identity(), true, 3);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> mergeDelayError(Publisher<? extends T> var0, Publisher<? extends T> var1, Publisher<? extends T> var2, Publisher<? extends T> var3) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      return fromArray(new Publisher[]{var0, var1, var2, var3}).flatMap(Functions.identity(), true, 4);
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> never() {
      return RxJavaPlugins.onAssembly(FlowableNever.INSTANCE);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static Flowable<Integer> range(int var0, int var1) {
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
         return RxJavaPlugins.onAssembly(new FlowableRange(var0, var1));
      }
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static Flowable<Long> rangeLong(long var0, long var2) {
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
         return RxJavaPlugins.onAssembly(new FlowableRangeLong(var0, var2));
      }
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Single<Boolean> sequenceEqual(Publisher<? extends T> var0, Publisher<? extends T> var1) {
      return sequenceEqual(var0, var1, ObjectHelper.equalsPredicate(), bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Single<Boolean> sequenceEqual(Publisher<? extends T> var0, Publisher<? extends T> var1, int var2) {
      return sequenceEqual(var0, var1, ObjectHelper.equalsPredicate(), var2);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Single<Boolean> sequenceEqual(Publisher<? extends T> var0, Publisher<? extends T> var1, BiPredicate<? super T, ? super T> var2) {
      return sequenceEqual(var0, var1, var2, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Single<Boolean> sequenceEqual(Publisher<? extends T> var0, Publisher<? extends T> var1, BiPredicate<? super T, ? super T> var2, int var3) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "isEqual is null");
      ObjectHelper.verifyPositive(var3, "bufferSize");
      return RxJavaPlugins.onAssembly(new FlowableSequenceEqualSingle(var0, var1, var2, var3));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> switchOnNext(Publisher<? extends Publisher<? extends T>> var0) {
      return fromPublisher(var0).switchMap(Functions.identity());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> switchOnNext(Publisher<? extends Publisher<? extends T>> var0, int var1) {
      return fromPublisher(var0).switchMap(Functions.identity(), var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> switchOnNextDelayError(Publisher<? extends Publisher<? extends T>> var0) {
      return switchOnNextDelayError(var0, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> switchOnNextDelayError(Publisher<? extends Publisher<? extends T>> var0, int var1) {
      return fromPublisher(var0).switchMapDelayError(Functions.identity(), var1);
   }

   private Flowable<T> timeout0(long var1, TimeUnit var3, Publisher<? extends T> var4, Scheduler var5) {
      ObjectHelper.requireNonNull(var3, "timeUnit is null");
      ObjectHelper.requireNonNull(var5, "scheduler is null");
      return RxJavaPlugins.onAssembly(new FlowableTimeoutTimed(this, var1, var3, var5, var4));
   }

   private <U extends Object, V extends Object> Flowable<T> timeout0(Publisher<U> var1, Function<? super T, ? extends Publisher<V>> var2, Publisher<? extends T> var3) {
      ObjectHelper.requireNonNull(var2, "itemTimeoutIndicator is null");
      return RxJavaPlugins.onAssembly(new FlowableTimeout(this, var1, var2, var3));
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public static Flowable<Long> timer(long var0, TimeUnit var2) {
      return timer(var0, var2, Schedulers.computation());
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public static Flowable<Long> timer(long var0, TimeUnit var2, Scheduler var3) {
      ObjectHelper.requireNonNull(var2, "unit is null");
      ObjectHelper.requireNonNull(var3, "scheduler is null");
      return RxJavaPlugins.onAssembly(new FlowableTimer(Math.max(0L, var0), var2, var3));
   }

   @BackpressureSupport(BackpressureKind.NONE)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object> Flowable<T> unsafeCreate(Publisher<T> var0) {
      ObjectHelper.requireNonNull(var0, "onSubscribe is null");
      if(var0 instanceof Flowable) {
         throw new IllegalArgumentException("unsafeCreate(Flowable) should be upgraded");
      } else {
         return RxJavaPlugins.onAssembly(new FlowableFromPublisher(var0));
      }
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, D extends Object> Flowable<T> using(Callable<? extends D> var0, Function<? super D, ? extends Publisher<? extends T>> var1, Consumer<? super D> var2) {
      return using(var0, var1, var2, true);
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, D extends Object> Flowable<T> using(Callable<? extends D> var0, Function<? super D, ? extends Publisher<? extends T>> var1, Consumer<? super D> var2, boolean var3) {
      ObjectHelper.requireNonNull(var0, "resourceSupplier is null");
      ObjectHelper.requireNonNull(var1, "sourceSupplier is null");
      ObjectHelper.requireNonNull(var2, "disposer is null");
      return RxJavaPlugins.onAssembly(new FlowableUsing(var0, var1, var2, var3));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Flowable<R> zip(Iterable<? extends Publisher<? extends T>> var0, Function<? super Object[], ? extends R> var1) {
      ObjectHelper.requireNonNull(var1, "zipper is null");
      ObjectHelper.requireNonNull(var0, "sources is null");
      return RxJavaPlugins.onAssembly(new FlowableZip((Publisher[])null, var0, var1, bufferSize(), false));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Flowable<R> zip(Publisher<? extends Publisher<? extends T>> var0, Function<? super Object[], ? extends R> var1) {
      ObjectHelper.requireNonNull(var1, "zipper is null");
      return fromPublisher(var0).toList().flatMapPublisher(FlowableInternalHelper.zipIterable(var1));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, R extends Object> Flowable<R> zip(Publisher<? extends T1> var0, Publisher<? extends T2> var1, BiFunction<? super T1, ? super T2, ? extends R> var2) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      return zipArray(Functions.toFunction(var2), false, bufferSize(), new Publisher[]{var0, var1});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, R extends Object> Flowable<R> zip(Publisher<? extends T1> var0, Publisher<? extends T2> var1, BiFunction<? super T1, ? super T2, ? extends R> var2, boolean var3) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      return zipArray(Functions.toFunction(var2), var3, bufferSize(), new Publisher[]{var0, var1});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, R extends Object> Flowable<R> zip(Publisher<? extends T1> var0, Publisher<? extends T2> var1, BiFunction<? super T1, ? super T2, ? extends R> var2, boolean var3, int var4) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      return zipArray(Functions.toFunction(var2), var3, var4, new Publisher[]{var0, var1});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, R extends Object> Flowable<R> zip(Publisher<? extends T1> var0, Publisher<? extends T2> var1, Publisher<? extends T3> var2, Function3<? super T1, ? super T2, ? super T3, ? extends R> var3) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      return zipArray(Functions.toFunction(var3), false, bufferSize(), new Publisher[]{var0, var1, var2});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, R extends Object> Flowable<R> zip(Publisher<? extends T1> var0, Publisher<? extends T2> var1, Publisher<? extends T3> var2, Publisher<? extends T4> var3, Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> var4) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      return zipArray(Functions.toFunction(var4), false, bufferSize(), new Publisher[]{var0, var1, var2, var3});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, T5 extends Object, R extends Object> Flowable<R> zip(Publisher<? extends T1> var0, Publisher<? extends T2> var1, Publisher<? extends T3> var2, Publisher<? extends T4> var3, Publisher<? extends T5> var4, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> var5) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      ObjectHelper.requireNonNull(var4, "source5 is null");
      return zipArray(Functions.toFunction(var5), false, bufferSize(), new Publisher[]{var0, var1, var2, var3, var4});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, T5 extends Object, T6 extends Object, R extends Object> Flowable<R> zip(Publisher<? extends T1> var0, Publisher<? extends T2> var1, Publisher<? extends T3> var2, Publisher<? extends T4> var3, Publisher<? extends T5> var4, Publisher<? extends T6> var5, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> var6) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      ObjectHelper.requireNonNull(var4, "source5 is null");
      ObjectHelper.requireNonNull(var5, "source6 is null");
      return zipArray(Functions.toFunction(var6), false, bufferSize(), new Publisher[]{var0, var1, var2, var3, var4, var5});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, T5 extends Object, T6 extends Object, T7 extends Object, R extends Object> Flowable<R> zip(Publisher<? extends T1> var0, Publisher<? extends T2> var1, Publisher<? extends T3> var2, Publisher<? extends T4> var3, Publisher<? extends T5> var4, Publisher<? extends T6> var5, Publisher<? extends T7> var6, Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> var7) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      ObjectHelper.requireNonNull(var4, "source5 is null");
      ObjectHelper.requireNonNull(var5, "source6 is null");
      ObjectHelper.requireNonNull(var6, "source7 is null");
      return zipArray(Functions.toFunction(var7), false, bufferSize(), new Publisher[]{var0, var1, var2, var3, var4, var5, var6});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, T5 extends Object, T6 extends Object, T7 extends Object, T8 extends Object, R extends Object> Flowable<R> zip(Publisher<? extends T1> var0, Publisher<? extends T2> var1, Publisher<? extends T3> var2, Publisher<? extends T4> var3, Publisher<? extends T5> var4, Publisher<? extends T6> var5, Publisher<? extends T7> var6, Publisher<? extends T8> var7, Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> var8) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      ObjectHelper.requireNonNull(var4, "source5 is null");
      ObjectHelper.requireNonNull(var5, "source6 is null");
      ObjectHelper.requireNonNull(var6, "source7 is null");
      ObjectHelper.requireNonNull(var7, "source8 is null");
      return zipArray(Functions.toFunction(var8), false, bufferSize(), new Publisher[]{var0, var1, var2, var3, var4, var5, var6, var7});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, T5 extends Object, T6 extends Object, T7 extends Object, T8 extends Object, T9 extends Object, R extends Object> Flowable<R> zip(Publisher<? extends T1> var0, Publisher<? extends T2> var1, Publisher<? extends T3> var2, Publisher<? extends T4> var3, Publisher<? extends T5> var4, Publisher<? extends T6> var5, Publisher<? extends T7> var6, Publisher<? extends T8> var7, Publisher<? extends T9> var8, Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> var9) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      ObjectHelper.requireNonNull(var4, "source5 is null");
      ObjectHelper.requireNonNull(var5, "source6 is null");
      ObjectHelper.requireNonNull(var6, "source7 is null");
      ObjectHelper.requireNonNull(var7, "source8 is null");
      ObjectHelper.requireNonNull(var8, "source9 is null");
      return zipArray(Functions.toFunction(var9), false, bufferSize(), new Publisher[]{var0, var1, var2, var3, var4, var5, var6, var7, var8});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Flowable<R> zipArray(Function<? super Object[], ? extends R> var0, boolean var1, int var2, Publisher<? extends T> ... var3) {
      if(var3.length == 0) {
         return empty();
      } else {
         ObjectHelper.requireNonNull(var0, "zipper is null");
         ObjectHelper.verifyPositive(var2, "bufferSize");
         return RxJavaPlugins.onAssembly(new FlowableZip(var3, (Iterable)null, var0, var2, var1));
      }
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T extends Object, R extends Object> Flowable<R> zipIterable(Iterable<? extends Publisher<? extends T>> var0, Function<? super Object[], ? extends R> var1, boolean var2, int var3) {
      ObjectHelper.requireNonNull(var1, "zipper is null");
      ObjectHelper.requireNonNull(var0, "sources is null");
      ObjectHelper.verifyPositive(var3, "bufferSize");
      return RxJavaPlugins.onAssembly(new FlowableZip((Publisher[])null, var0, var1, var3, var2));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<Boolean> all(Predicate<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "predicate is null");
      return RxJavaPlugins.onAssembly(new FlowableAllSingle(this, var1));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> ambWith(Publisher<? extends T> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return ambArray(new Publisher[]{this, var1});
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<Boolean> any(Predicate<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "predicate is null");
      return RxJavaPlugins.onAssembly(new FlowableAnySingle(this, var1));
   }

   @BackpressureSupport(BackpressureKind.SPECIAL)
   @CheckReturnValue
   @SchedulerSupport("none")
   @Experimental
   public final <R extends Object> R as(@NonNull FlowableConverter<T, ? extends R> var1) {
      return ((FlowableConverter)ObjectHelper.requireNonNull(var1, "converter is null")).apply(this);
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final T blockingFirst() {
      BlockingFirstSubscriber var1 = new BlockingFirstSubscriber();
      this.subscribe((FlowableSubscriber)var1);
      Object var2 = var1.blockingGet();
      if(var2 != null) {
         return var2;
      } else {
         throw new NoSuchElementException();
      }
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final T blockingFirst(T var1) {
      BlockingFirstSubscriber var2 = new BlockingFirstSubscriber();
      this.subscribe((FlowableSubscriber)var2);
      Object var3 = var2.blockingGet();
      if(var3 != null) {
         var1 = var3;
      }

      return var1;
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
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

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Iterable<T> blockingIterable() {
      return this.blockingIterable(bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Iterable<T> blockingIterable(int var1) {
      ObjectHelper.verifyPositive(var1, "bufferSize");
      return new BlockingFlowableIterable(this, var1);
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final T blockingLast() {
      BlockingLastSubscriber var1 = new BlockingLastSubscriber();
      this.subscribe((FlowableSubscriber)var1);
      Object var2 = var1.blockingGet();
      if(var2 != null) {
         return var2;
      } else {
         throw new NoSuchElementException();
      }
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final T blockingLast(T var1) {
      BlockingLastSubscriber var2 = new BlockingLastSubscriber();
      this.subscribe((FlowableSubscriber)var2);
      Object var3 = var2.blockingGet();
      if(var3 != null) {
         var1 = var3;
      }

      return var1;
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Iterable<T> blockingLatest() {
      return new BlockingFlowableLatest(this);
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Iterable<T> blockingMostRecent(T var1) {
      return new BlockingFlowableMostRecent(this, var1);
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Iterable<T> blockingNext() {
      return new BlockingFlowableNext(this);
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final T blockingSingle() {
      return this.singleOrError().blockingGet();
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final T blockingSingle(T var1) {
      return this.single(var1).blockingGet();
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @SchedulerSupport("none")
   public final void blockingSubscribe() {
      FlowableBlockingSubscribe.subscribe(this);
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @SchedulerSupport("none")
   public final void blockingSubscribe(Consumer<? super T> var1) {
      FlowableBlockingSubscribe.subscribe(this, var1, Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION);
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @SchedulerSupport("none")
   public final void blockingSubscribe(Consumer<? super T> var1, Consumer<? super Throwable> var2) {
      FlowableBlockingSubscribe.subscribe(this, var1, var2, Functions.EMPTY_ACTION);
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @SchedulerSupport("none")
   public final void blockingSubscribe(Consumer<? super T> var1, Consumer<? super Throwable> var2, Action var3) {
      FlowableBlockingSubscribe.subscribe(this, var1, var2, var3);
   }

   @BackpressureSupport(BackpressureKind.SPECIAL)
   @SchedulerSupport("none")
   public final void blockingSubscribe(Subscriber<? super T> var1) {
      FlowableBlockingSubscribe.subscribe(this, var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<List<T>> buffer(int var1) {
      return this.buffer(var1, var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<List<T>> buffer(int var1, int var2) {
      return this.buffer(var1, var2, ArrayListSupplier.asCallable());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object & Collection<? super T>> Flowable<U> buffer(int var1, int var2, Callable<U> var3) {
      ObjectHelper.verifyPositive(var1, "count");
      ObjectHelper.verifyPositive(var2, "skip");
      ObjectHelper.requireNonNull(var3, "bufferSupplier is null");
      return RxJavaPlugins.onAssembly(new FlowableBuffer(this, var1, var2, var3));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object & Collection<? super T>> Flowable<U> buffer(int var1, Callable<U> var2) {
      return this.buffer(var1, var1, var2);
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Flowable<List<T>> buffer(long var1, long var3, TimeUnit var5) {
      return this.buffer(var1, var3, var5, Schedulers.computation(), ArrayListSupplier.asCallable());
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<List<T>> buffer(long var1, long var3, TimeUnit var5, Scheduler var6) {
      return this.buffer(var1, var3, var5, var6, ArrayListSupplier.asCallable());
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final <U extends Object & Collection<? super T>> Flowable<U> buffer(long var1, long var3, TimeUnit var5, Scheduler var6, Callable<U> var7) {
      ObjectHelper.requireNonNull(var5, "unit is null");
      ObjectHelper.requireNonNull(var6, "scheduler is null");
      ObjectHelper.requireNonNull(var7, "bufferSupplier is null");
      return RxJavaPlugins.onAssembly(new FlowableBufferTimed(this, var1, var3, var5, var6, var7, Integer.MAX_VALUE, false));
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Flowable<List<T>> buffer(long var1, TimeUnit var3) {
      return this.buffer(var1, var3, Schedulers.computation(), Integer.MAX_VALUE);
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Flowable<List<T>> buffer(long var1, TimeUnit var3, int var4) {
      return this.buffer(var1, var3, Schedulers.computation(), var4);
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<List<T>> buffer(long var1, TimeUnit var3, Scheduler var4) {
      return this.buffer(var1, var3, var4, Integer.MAX_VALUE, ArrayListSupplier.asCallable(), false);
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<List<T>> buffer(long var1, TimeUnit var3, Scheduler var4, int var5) {
      return this.buffer(var1, var3, var4, var5, ArrayListSupplier.asCallable(), false);
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final <U extends Object & Collection<? super T>> Flowable<U> buffer(long var1, TimeUnit var3, Scheduler var4, int var5, Callable<U> var6, boolean var7) {
      ObjectHelper.requireNonNull(var3, "unit is null");
      ObjectHelper.requireNonNull(var4, "scheduler is null");
      ObjectHelper.requireNonNull(var6, "bufferSupplier is null");
      ObjectHelper.verifyPositive(var5, "count");
      return RxJavaPlugins.onAssembly(new FlowableBufferTimed(this, var1, var1, var3, var4, var6, var5, var7));
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <TOpening extends Object, TClosing extends Object> Flowable<List<T>> buffer(Flowable<? extends TOpening> var1, Function<? super TOpening, ? extends Publisher<? extends TClosing>> var2) {
      return this.buffer(var1, var2, ArrayListSupplier.asCallable());
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <TOpening extends Object, TClosing extends Object, U extends Object & Collection<? super T>> Flowable<U> buffer(Flowable<? extends TOpening> var1, Function<? super TOpening, ? extends Publisher<? extends TClosing>> var2, Callable<U> var3) {
      ObjectHelper.requireNonNull(var1, "openingIndicator is null");
      ObjectHelper.requireNonNull(var2, "closingIndicator is null");
      ObjectHelper.requireNonNull(var3, "bufferSupplier is null");
      return RxJavaPlugins.onAssembly(new FlowableBufferBoundary(this, var1, var2, var3));
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <B extends Object> Flowable<List<T>> buffer(Callable<? extends Publisher<B>> var1) {
      return this.buffer(var1, ArrayListSupplier.asCallable());
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <B extends Object, U extends Object & Collection<? super T>> Flowable<U> buffer(Callable<? extends Publisher<B>> var1, Callable<U> var2) {
      ObjectHelper.requireNonNull(var1, "boundaryIndicatorSupplier is null");
      ObjectHelper.requireNonNull(var2, "bufferSupplier is null");
      return RxJavaPlugins.onAssembly(new FlowableBufferBoundarySupplier(this, var1, var2));
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <B extends Object> Flowable<List<T>> buffer(Publisher<B> var1) {
      return this.buffer(var1, ArrayListSupplier.asCallable());
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <B extends Object> Flowable<List<T>> buffer(Publisher<B> var1, int var2) {
      ObjectHelper.verifyPositive(var2, "initialCapacity");
      return this.buffer(var1, Functions.createArrayList(var2));
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <B extends Object, U extends Object & Collection<? super T>> Flowable<U> buffer(Publisher<B> var1, Callable<U> var2) {
      ObjectHelper.requireNonNull(var1, "boundaryIndicator is null");
      ObjectHelper.requireNonNull(var2, "bufferSupplier is null");
      return RxJavaPlugins.onAssembly(new FlowableBufferExactBoundary(this, var1, var2));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> cache() {
      return this.cacheWithInitialCapacity(16);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> cacheWithInitialCapacity(int var1) {
      ObjectHelper.verifyPositive(var1, "initialCapacity");
      return RxJavaPlugins.onAssembly(new FlowableCache(this, var1));
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Flowable<U> cast(Class<U> var1) {
      ObjectHelper.requireNonNull(var1, "clazz is null");
      return this.map(Functions.castFunction(var1));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Single<U> collect(Callable<? extends U> var1, BiConsumer<? super U, ? super T> var2) {
      ObjectHelper.requireNonNull(var1, "initialItemSupplier is null");
      ObjectHelper.requireNonNull(var2, "collector is null");
      return RxJavaPlugins.onAssembly(new FlowableCollectSingle(this, var1, var2));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Single<U> collectInto(U var1, BiConsumer<? super U, ? super T> var2) {
      ObjectHelper.requireNonNull(var1, "initialItem is null");
      return this.collect(Functions.justCallable(var1), var2);
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> compose(FlowableTransformer<? super T, ? extends R> var1) {
      return fromPublisher(((FlowableTransformer)ObjectHelper.requireNonNull(var1, "composer is null")).apply(this));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> concatMap(Function<? super T, ? extends Publisher<? extends R>> var1) {
      return this.concatMap(var1, 2);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> concatMap(Function<? super T, ? extends Publisher<? extends R>> var1, int var2) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.verifyPositive(var2, "prefetch");
      if(this instanceof ScalarCallable) {
         Object var3 = ((ScalarCallable)this).call();
         return var3 == null?empty():FlowableScalarXMap.scalarXMap(var3, var1);
      } else {
         return RxJavaPlugins.onAssembly(new FlowableConcatMap(this, var1, var2, ErrorMode.IMMEDIATE));
      }
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> concatMapDelayError(Function<? super T, ? extends Publisher<? extends R>> var1) {
      return this.concatMapDelayError(var1, 2, true);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> concatMapDelayError(Function<? super T, ? extends Publisher<? extends R>> var1, int var2, boolean var3) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.verifyPositive(var2, "prefetch");
      if(this instanceof ScalarCallable) {
         Object var5 = ((ScalarCallable)this).call();
         return var5 == null?empty():FlowableScalarXMap.scalarXMap(var5, var1);
      } else {
         ErrorMode var4;
         if(var3) {
            var4 = ErrorMode.END;
         } else {
            var4 = ErrorMode.BOUNDARY;
         }

         return RxJavaPlugins.onAssembly(new FlowableConcatMap(this, var1, var2, var4));
      }
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> concatMapEager(Function<? super T, ? extends Publisher<? extends R>> var1) {
      return this.concatMapEager(var1, bufferSize(), bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> concatMapEager(Function<? super T, ? extends Publisher<? extends R>> var1, int var2, int var3) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.verifyPositive(var2, "maxConcurrency");
      ObjectHelper.verifyPositive(var3, "prefetch");
      return RxJavaPlugins.onAssembly(new FlowableConcatMapEager(this, var1, var2, var3, ErrorMode.IMMEDIATE));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> concatMapEagerDelayError(Function<? super T, ? extends Publisher<? extends R>> var1, int var2, int var3, boolean var4) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.verifyPositive(var2, "maxConcurrency");
      ObjectHelper.verifyPositive(var3, "prefetch");
      ErrorMode var5;
      if(var4) {
         var5 = ErrorMode.END;
      } else {
         var5 = ErrorMode.BOUNDARY;
      }

      return RxJavaPlugins.onAssembly(new FlowableConcatMapEager(this, var1, var2, var3, var5));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> concatMapEagerDelayError(Function<? super T, ? extends Publisher<? extends R>> var1, boolean var2) {
      return this.concatMapEagerDelayError(var1, bufferSize(), bufferSize(), var2);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Flowable<U> concatMapIterable(Function<? super T, ? extends Iterable<? extends U>> var1) {
      return this.concatMapIterable(var1, 2);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Flowable<U> concatMapIterable(Function<? super T, ? extends Iterable<? extends U>> var1, int var2) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.verifyPositive(var2, "prefetch");
      return RxJavaPlugins.onAssembly(new FlowableFlattenIterable(this, var1, var2));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> concatWith(Publisher<? extends T> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return concat(this, var1);
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<Boolean> contains(Object var1) {
      ObjectHelper.requireNonNull(var1, "item is null");
      return this.any(Functions.equalsWith(var1));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<Long> count() {
      return RxJavaPlugins.onAssembly(new FlowableCountSingle(this));
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Flowable<T> debounce(long var1, TimeUnit var3) {
      return this.debounce(var1, var3, Schedulers.computation());
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<T> debounce(long var1, TimeUnit var3, Scheduler var4) {
      ObjectHelper.requireNonNull(var3, "unit is null");
      ObjectHelper.requireNonNull(var4, "scheduler is null");
      return RxJavaPlugins.onAssembly(new FlowableDebounceTimed(this, var1, var3, var4));
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Flowable<T> debounce(Function<? super T, ? extends Publisher<U>> var1) {
      ObjectHelper.requireNonNull(var1, "debounceIndicator is null");
      return RxJavaPlugins.onAssembly(new FlowableDebounce(this, var1));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> defaultIfEmpty(T var1) {
      ObjectHelper.requireNonNull(var1, "item is null");
      return this.switchIfEmpty(just(var1));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Flowable<T> delay(long var1, TimeUnit var3) {
      return this.delay(var1, var3, Schedulers.computation(), false);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<T> delay(long var1, TimeUnit var3, Scheduler var4) {
      return this.delay(var1, var3, var4, false);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<T> delay(long var1, TimeUnit var3, Scheduler var4, boolean var5) {
      ObjectHelper.requireNonNull(var3, "unit is null");
      ObjectHelper.requireNonNull(var4, "scheduler is null");
      return RxJavaPlugins.onAssembly(new FlowableDelay(this, Math.max(0L, var1), var3, var4, var5));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Flowable<T> delay(long var1, TimeUnit var3, boolean var4) {
      return this.delay(var1, var3, Schedulers.computation(), var4);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Flowable<T> delay(Function<? super T, ? extends Publisher<U>> var1) {
      ObjectHelper.requireNonNull(var1, "itemDelayIndicator is null");
      return this.flatMap(FlowableInternalHelper.itemDelay(var1));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, V extends Object> Flowable<T> delay(Publisher<U> var1, Function<? super T, ? extends Publisher<V>> var2) {
      return this.delaySubscription(var1).delay(var2);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Flowable<T> delaySubscription(long var1, TimeUnit var3) {
      return this.delaySubscription(var1, var3, Schedulers.computation());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<T> delaySubscription(long var1, TimeUnit var3, Scheduler var4) {
      return this.delaySubscription(timer(var1, var3, var4));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Flowable<T> delaySubscription(Publisher<U> var1) {
      ObjectHelper.requireNonNull(var1, "subscriptionIndicator is null");
      return RxJavaPlugins.onAssembly(new FlowableDelaySubscriptionOther(this, var1));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T2 extends Object> Flowable<T2> dematerialize() {
      return RxJavaPlugins.onAssembly(new FlowableDematerialize(this));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> distinct() {
      return this.distinct(Functions.identity(), Functions.createHashSet());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <K extends Object> Flowable<T> distinct(Function<? super T, K> var1) {
      return this.distinct(var1, Functions.createHashSet());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <K extends Object> Flowable<T> distinct(Function<? super T, K> var1, Callable<? extends Collection<? super K>> var2) {
      ObjectHelper.requireNonNull(var1, "keySelector is null");
      ObjectHelper.requireNonNull(var2, "collectionSupplier is null");
      return RxJavaPlugins.onAssembly(new FlowableDistinct(this, var1, var2));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> distinctUntilChanged() {
      return this.distinctUntilChanged(Functions.identity());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> distinctUntilChanged(BiPredicate<? super T, ? super T> var1) {
      ObjectHelper.requireNonNull(var1, "comparer is null");
      return RxJavaPlugins.onAssembly(new FlowableDistinctUntilChanged(this, Functions.identity(), var1));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <K extends Object> Flowable<T> distinctUntilChanged(Function<? super T, K> var1) {
      ObjectHelper.requireNonNull(var1, "keySelector is null");
      return RxJavaPlugins.onAssembly(new FlowableDistinctUntilChanged(this, var1, ObjectHelper.equalsPredicate()));
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> doAfterNext(Consumer<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "onAfterNext is null");
      return RxJavaPlugins.onAssembly(new FlowableDoAfterNext(this, var1));
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> doAfterTerminate(Action var1) {
      return this.doOnEach(Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, var1);
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> doFinally(Action var1) {
      ObjectHelper.requireNonNull(var1, "onFinally is null");
      return RxJavaPlugins.onAssembly(new FlowableDoFinally(this, var1));
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> doOnCancel(Action var1) {
      return this.doOnLifecycle(Functions.emptyConsumer(), Functions.EMPTY_LONG_CONSUMER, var1);
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> doOnComplete(Action var1) {
      return this.doOnEach(Functions.emptyConsumer(), Functions.emptyConsumer(), var1, Functions.EMPTY_ACTION);
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> doOnEach(Consumer<? super Notification<T>> var1) {
      ObjectHelper.requireNonNull(var1, "consumer is null");
      return this.doOnEach(Functions.notificationOnNext(var1), Functions.notificationOnError(var1), Functions.notificationOnComplete(var1), Functions.EMPTY_ACTION);
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> doOnEach(Subscriber<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "subscriber is null");
      return this.doOnEach(FlowableInternalHelper.subscriberOnNext(var1), FlowableInternalHelper.subscriberOnError(var1), FlowableInternalHelper.subscriberOnComplete(var1), Functions.EMPTY_ACTION);
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> doOnError(Consumer<? super Throwable> var1) {
      return this.doOnEach(Functions.emptyConsumer(), var1, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION);
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> doOnLifecycle(Consumer<? super Subscription> var1, LongConsumer var2, Action var3) {
      ObjectHelper.requireNonNull(var1, "onSubscribe is null");
      ObjectHelper.requireNonNull(var2, "onRequest is null");
      ObjectHelper.requireNonNull(var3, "onCancel is null");
      return RxJavaPlugins.onAssembly(new FlowableDoOnLifecycle(this, var1, var2, var3));
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> doOnNext(Consumer<? super T> var1) {
      return this.doOnEach(var1, Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION);
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> doOnRequest(LongConsumer var1) {
      return this.doOnLifecycle(Functions.emptyConsumer(), var1, Functions.EMPTY_ACTION);
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> doOnSubscribe(Consumer<? super Subscription> var1) {
      return this.doOnLifecycle(var1, Functions.EMPTY_LONG_CONSUMER, Functions.EMPTY_ACTION);
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> doOnTerminate(Action var1) {
      return this.doOnEach(Functions.emptyConsumer(), Functions.actionConsumer(var1), var1, Functions.EMPTY_ACTION);
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> elementAt(long var1) {
      if(var1 < 0L) {
         StringBuilder var3 = new StringBuilder();
         var3.append("index >= 0 required but it was ");
         var3.append(var1);
         throw new IndexOutOfBoundsException(var3.toString());
      } else {
         return RxJavaPlugins.onAssembly(new FlowableElementAtMaybe(this, var1));
      }
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
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
         return RxJavaPlugins.onAssembly(new FlowableElementAtSingle(this, var1, var3));
      }
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> elementAtOrError(long var1) {
      if(var1 < 0L) {
         StringBuilder var3 = new StringBuilder();
         var3.append("index >= 0 required but it was ");
         var3.append(var1);
         throw new IndexOutOfBoundsException(var3.toString());
      } else {
         return RxJavaPlugins.onAssembly(new FlowableElementAtSingle(this, var1, (Object)null));
      }
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> filter(Predicate<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "predicate is null");
      return RxJavaPlugins.onAssembly(new FlowableFilter(this, var1));
   }

   @BackpressureSupport(BackpressureKind.SPECIAL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> first(T var1) {
      return this.elementAt(0L, var1);
   }

   @BackpressureSupport(BackpressureKind.SPECIAL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> firstElement() {
      return this.elementAt(0L);
   }

   @BackpressureSupport(BackpressureKind.SPECIAL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> firstOrError() {
      return this.elementAtOrError(0L);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> var1) {
      return this.flatMap(var1, false, bufferSize(), bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> var1, int var2) {
      return this.flatMap(var1, false, var2, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, R extends Object> Flowable<R> flatMap(Function<? super T, ? extends Publisher<? extends U>> var1, BiFunction<? super T, ? super U, ? extends R> var2) {
      return this.flatMap(var1, var2, false, bufferSize(), bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, R extends Object> Flowable<R> flatMap(Function<? super T, ? extends Publisher<? extends U>> var1, BiFunction<? super T, ? super U, ? extends R> var2, int var3) {
      return this.flatMap(var1, var2, false, var3, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, R extends Object> Flowable<R> flatMap(Function<? super T, ? extends Publisher<? extends U>> var1, BiFunction<? super T, ? super U, ? extends R> var2, boolean var3) {
      return this.flatMap(var1, var2, var3, bufferSize(), bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, R extends Object> Flowable<R> flatMap(Function<? super T, ? extends Publisher<? extends U>> var1, BiFunction<? super T, ? super U, ? extends R> var2, boolean var3, int var4) {
      return this.flatMap(var1, var2, var3, var4, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, R extends Object> Flowable<R> flatMap(Function<? super T, ? extends Publisher<? extends U>> var1, BiFunction<? super T, ? super U, ? extends R> var2, boolean var3, int var4, int var5) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.requireNonNull(var2, "combiner is null");
      ObjectHelper.verifyPositive(var4, "maxConcurrency");
      ObjectHelper.verifyPositive(var5, "bufferSize");
      return this.flatMap(FlowableInternalHelper.flatMapWithCombiner(var1, var2), var3, var4, var5);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> var1, Function<? super Throwable, ? extends Publisher<? extends R>> var2, Callable<? extends Publisher<? extends R>> var3) {
      ObjectHelper.requireNonNull(var1, "onNextMapper is null");
      ObjectHelper.requireNonNull(var2, "onErrorMapper is null");
      ObjectHelper.requireNonNull(var3, "onCompleteSupplier is null");
      return merge((Publisher)(new FlowableMapNotification(this, var1, var2, var3)));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> var1, Function<Throwable, ? extends Publisher<? extends R>> var2, Callable<? extends Publisher<? extends R>> var3, int var4) {
      ObjectHelper.requireNonNull(var1, "onNextMapper is null");
      ObjectHelper.requireNonNull(var2, "onErrorMapper is null");
      ObjectHelper.requireNonNull(var3, "onCompleteSupplier is null");
      return merge((Publisher)(new FlowableMapNotification(this, var1, var2, var3)), var4);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> var1, boolean var2) {
      return this.flatMap(var1, var2, bufferSize(), bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> var1, boolean var2, int var3) {
      return this.flatMap(var1, var2, var3, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> var1, boolean var2, int var3, int var4) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.verifyPositive(var3, "maxConcurrency");
      ObjectHelper.verifyPositive(var4, "bufferSize");
      if(this instanceof ScalarCallable) {
         Object var5 = ((ScalarCallable)this).call();
         return var5 == null?empty():FlowableScalarXMap.scalarXMap(var5, var1);
      } else {
         return RxJavaPlugins.onAssembly(new FlowableFlatMap(this, var1, var2, var3, var4));
      }
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable flatMapCompletable(Function<? super T, ? extends CompletableSource> var1) {
      return this.flatMapCompletable(var1, false, Integer.MAX_VALUE);
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable flatMapCompletable(Function<? super T, ? extends CompletableSource> var1, boolean var2, int var3) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.verifyPositive(var3, "maxConcurrency");
      return RxJavaPlugins.onAssembly(new FlowableFlatMapCompletableCompletable(this, var1, var2, var3));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Flowable<U> flatMapIterable(Function<? super T, ? extends Iterable<? extends U>> var1) {
      return this.flatMapIterable(var1, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Flowable<U> flatMapIterable(Function<? super T, ? extends Iterable<? extends U>> var1, int var2) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.verifyPositive(var2, "bufferSize");
      return RxJavaPlugins.onAssembly(new FlowableFlattenIterable(this, var1, var2));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, V extends Object> Flowable<V> flatMapIterable(Function<? super T, ? extends Iterable<? extends U>> var1, BiFunction<? super T, ? super U, ? extends V> var2) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.requireNonNull(var2, "resultSelector is null");
      return this.flatMap(FlowableInternalHelper.flatMapIntoIterable(var1), var2, false, bufferSize(), bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, V extends Object> Flowable<V> flatMapIterable(Function<? super T, ? extends Iterable<? extends U>> var1, BiFunction<? super T, ? super U, ? extends V> var2, int var3) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.requireNonNull(var2, "resultSelector is null");
      return this.flatMap(FlowableInternalHelper.flatMapIntoIterable(var1), var2, false, bufferSize(), var3);
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> flatMapMaybe(Function<? super T, ? extends MaybeSource<? extends R>> var1) {
      return this.flatMapMaybe(var1, false, Integer.MAX_VALUE);
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> flatMapMaybe(Function<? super T, ? extends MaybeSource<? extends R>> var1, boolean var2, int var3) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.verifyPositive(var3, "maxConcurrency");
      return RxJavaPlugins.onAssembly(new FlowableFlatMapMaybe(this, var1, var2, var3));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> flatMapSingle(Function<? super T, ? extends SingleSource<? extends R>> var1) {
      return this.flatMapSingle(var1, false, Integer.MAX_VALUE);
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> flatMapSingle(Function<? super T, ? extends SingleSource<? extends R>> var1, boolean var2, int var3) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.verifyPositive(var3, "maxConcurrency");
      return RxJavaPlugins.onAssembly(new FlowableFlatMapSingle(this, var1, var2, var3));
   }

   @BackpressureSupport(BackpressureKind.NONE)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Disposable forEach(Consumer<? super T> var1) {
      return this.subscribe(var1);
   }

   @BackpressureSupport(BackpressureKind.NONE)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Disposable forEachWhile(Predicate<? super T> var1) {
      return this.forEachWhile(var1, Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION);
   }

   @BackpressureSupport(BackpressureKind.NONE)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Disposable forEachWhile(Predicate<? super T> var1, Consumer<? super Throwable> var2) {
      return this.forEachWhile(var1, var2, Functions.EMPTY_ACTION);
   }

   @BackpressureSupport(BackpressureKind.NONE)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Disposable forEachWhile(Predicate<? super T> var1, Consumer<? super Throwable> var2, Action var3) {
      ObjectHelper.requireNonNull(var1, "onNext is null");
      ObjectHelper.requireNonNull(var2, "onError is null");
      ObjectHelper.requireNonNull(var3, "onComplete is null");
      ForEachWhileSubscriber var4 = new ForEachWhileSubscriber(var1, var2, var3);
      this.subscribe((FlowableSubscriber)var4);
      return var4;
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <K extends Object> Flowable<GroupedFlowable<K, T>> groupBy(Function<? super T, ? extends K> var1) {
      return this.groupBy(var1, Functions.identity(), false, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <K extends Object, V extends Object> Flowable<GroupedFlowable<K, V>> groupBy(Function<? super T, ? extends K> var1, Function<? super T, ? extends V> var2) {
      return this.groupBy(var1, var2, false, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <K extends Object, V extends Object> Flowable<GroupedFlowable<K, V>> groupBy(Function<? super T, ? extends K> var1, Function<? super T, ? extends V> var2, boolean var3) {
      return this.groupBy(var1, var2, var3, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <K extends Object, V extends Object> Flowable<GroupedFlowable<K, V>> groupBy(Function<? super T, ? extends K> var1, Function<? super T, ? extends V> var2, boolean var3, int var4) {
      ObjectHelper.requireNonNull(var1, "keySelector is null");
      ObjectHelper.requireNonNull(var2, "valueSelector is null");
      ObjectHelper.verifyPositive(var4, "bufferSize");
      return RxJavaPlugins.onAssembly(new FlowableGroupBy(this, var1, var2, var4, var3));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <K extends Object> Flowable<GroupedFlowable<K, T>> groupBy(Function<? super T, ? extends K> var1, boolean var2) {
      return this.groupBy(var1, Functions.identity(), var2, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <TRight extends Object, TLeftEnd extends Object, TRightEnd extends Object, R extends Object> Flowable<R> groupJoin(Publisher<? extends TRight> var1, Function<? super T, ? extends Publisher<TLeftEnd>> var2, Function<? super TRight, ? extends Publisher<TRightEnd>> var3, BiFunction<? super T, ? super Flowable<TRight>, ? extends R> var4) {
      ObjectHelper.requireNonNull(var1, "other is null");
      ObjectHelper.requireNonNull(var2, "leftEnd is null");
      ObjectHelper.requireNonNull(var3, "rightEnd is null");
      ObjectHelper.requireNonNull(var4, "resultSelector is null");
      return RxJavaPlugins.onAssembly(new FlowableGroupJoin(this, var1, var2, var3, var4));
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> hide() {
      return RxJavaPlugins.onAssembly(new FlowableHide(this));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable ignoreElements() {
      return RxJavaPlugins.onAssembly(new FlowableIgnoreElementsCompletable(this));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<Boolean> isEmpty() {
      return this.all(Functions.alwaysFalse());
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <TRight extends Object, TLeftEnd extends Object, TRightEnd extends Object, R extends Object> Flowable<R> join(Publisher<? extends TRight> var1, Function<? super T, ? extends Publisher<TLeftEnd>> var2, Function<? super TRight, ? extends Publisher<TRightEnd>> var3, BiFunction<? super T, ? super TRight, ? extends R> var4) {
      ObjectHelper.requireNonNull(var1, "other is null");
      ObjectHelper.requireNonNull(var2, "leftEnd is null");
      ObjectHelper.requireNonNull(var3, "rightEnd is null");
      ObjectHelper.requireNonNull(var4, "resultSelector is null");
      return RxJavaPlugins.onAssembly(new FlowableJoin(this, var1, var2, var3, var4));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> last(T var1) {
      ObjectHelper.requireNonNull(var1, "defaultItem");
      return RxJavaPlugins.onAssembly(new FlowableLastSingle(this, var1));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> lastElement() {
      return RxJavaPlugins.onAssembly(new FlowableLastMaybe(this));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> lastOrError() {
      return RxJavaPlugins.onAssembly(new FlowableLastSingle(this, (Object)null));
   }

   @BackpressureSupport(BackpressureKind.SPECIAL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> lift(FlowableOperator<? extends R, ? super T> var1) {
      ObjectHelper.requireNonNull(var1, "lifter is null");
      return RxJavaPlugins.onAssembly(new FlowableLift(this, var1));
   }

   @BackpressureSupport(BackpressureKind.SPECIAL)
   @CheckReturnValue
   @SchedulerSupport("none")
   @Experimental
   public final Flowable<T> limit(long var1) {
      if(var1 < 0L) {
         StringBuilder var3 = new StringBuilder();
         var3.append("count >= 0 required but it was ");
         var3.append(var1);
         throw new IllegalArgumentException(var3.toString());
      } else {
         return RxJavaPlugins.onAssembly(new FlowableLimit(this, var1));
      }
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> map(Function<? super T, ? extends R> var1) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      return RxJavaPlugins.onAssembly(new FlowableMap(this, var1));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<Notification<T>> materialize() {
      return RxJavaPlugins.onAssembly(new FlowableMaterialize(this));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> mergeWith(Publisher<? extends T> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return merge(this, var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<T> observeOn(Scheduler var1) {
      return this.observeOn(var1, false, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<T> observeOn(Scheduler var1, boolean var2) {
      return this.observeOn(var1, var2, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<T> observeOn(Scheduler var1, boolean var2, int var3) {
      ObjectHelper.requireNonNull(var1, "scheduler is null");
      ObjectHelper.verifyPositive(var3, "bufferSize");
      return RxJavaPlugins.onAssembly(new FlowableObserveOn(this, var1, var2, var3));
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Flowable<U> ofType(Class<U> var1) {
      ObjectHelper.requireNonNull(var1, "clazz is null");
      return this.filter(Functions.isInstanceOf(var1)).cast(var1);
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> onBackpressureBuffer() {
      return this.onBackpressureBuffer(bufferSize(), false, true);
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> onBackpressureBuffer(int var1) {
      return this.onBackpressureBuffer(var1, false, false);
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> onBackpressureBuffer(int var1, Action var2) {
      return this.onBackpressureBuffer(var1, false, false, var2);
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> onBackpressureBuffer(int var1, boolean var2) {
      return this.onBackpressureBuffer(var1, var2, false);
   }

   @BackpressureSupport(BackpressureKind.SPECIAL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> onBackpressureBuffer(int var1, boolean var2, boolean var3) {
      ObjectHelper.verifyPositive(var1, "bufferSize");
      return RxJavaPlugins.onAssembly(new FlowableOnBackpressureBuffer(this, var1, var3, var2, Functions.EMPTY_ACTION));
   }

   @BackpressureSupport(BackpressureKind.SPECIAL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> onBackpressureBuffer(int var1, boolean var2, boolean var3, Action var4) {
      ObjectHelper.requireNonNull(var4, "onOverflow is null");
      ObjectHelper.verifyPositive(var1, "capacity");
      return RxJavaPlugins.onAssembly(new FlowableOnBackpressureBuffer(this, var1, var3, var2, var4));
   }

   @BackpressureSupport(BackpressureKind.SPECIAL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> onBackpressureBuffer(long var1, Action var3, BackpressureOverflowStrategy var4) {
      ObjectHelper.requireNonNull(var4, "strategy is null");
      ObjectHelper.verifyPositive(var1, "capacity");
      return RxJavaPlugins.onAssembly(new FlowableOnBackpressureBufferStrategy(this, var1, var3, var4));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> onBackpressureBuffer(boolean var1) {
      return this.onBackpressureBuffer(bufferSize(), var1, true);
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> onBackpressureDrop() {
      return RxJavaPlugins.onAssembly(new FlowableOnBackpressureDrop(this));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> onBackpressureDrop(Consumer<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "onDrop is null");
      return RxJavaPlugins.onAssembly(new FlowableOnBackpressureDrop(this, var1));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> onBackpressureLatest() {
      return RxJavaPlugins.onAssembly(new FlowableOnBackpressureLatest(this));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> onErrorResumeNext(Function<? super Throwable, ? extends Publisher<? extends T>> var1) {
      ObjectHelper.requireNonNull(var1, "resumeFunction is null");
      return RxJavaPlugins.onAssembly(new FlowableOnErrorNext(this, var1, false));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> onErrorResumeNext(Publisher<? extends T> var1) {
      ObjectHelper.requireNonNull(var1, "next is null");
      return this.onErrorResumeNext(Functions.justFunction(var1));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> onErrorReturn(Function<? super Throwable, ? extends T> var1) {
      ObjectHelper.requireNonNull(var1, "valueSupplier is null");
      return RxJavaPlugins.onAssembly(new FlowableOnErrorReturn(this, var1));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> onErrorReturnItem(T var1) {
      ObjectHelper.requireNonNull(var1, "item is null");
      return this.onErrorReturn(Functions.justFunction(var1));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> onExceptionResumeNext(Publisher<? extends T> var1) {
      ObjectHelper.requireNonNull(var1, "next is null");
      return RxJavaPlugins.onAssembly(new FlowableOnErrorNext(this, Functions.justFunction(var1), true));
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> onTerminateDetach() {
      return RxJavaPlugins.onAssembly(new FlowableDetach(this));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   @Beta
   public final ParallelFlowable<T> parallel() {
      return ParallelFlowable.from(this);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   @Beta
   public final ParallelFlowable<T> parallel(int var1) {
      ObjectHelper.verifyPositive(var1, "parallelism");
      return ParallelFlowable.from(this, var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   @Beta
   public final ParallelFlowable<T> parallel(int var1, int var2) {
      ObjectHelper.verifyPositive(var1, "parallelism");
      ObjectHelper.verifyPositive(var2, "prefetch");
      return ParallelFlowable.from(this, var1, var2);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> publish(Function<? super Flowable<T>, ? extends Publisher<R>> var1) {
      return this.publish(var1, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> publish(Function<? super Flowable<T>, ? extends Publisher<? extends R>> var1, int var2) {
      ObjectHelper.requireNonNull(var1, "selector is null");
      ObjectHelper.verifyPositive(var2, "prefetch");
      return RxJavaPlugins.onAssembly(new FlowablePublishMulticast(this, var1, var2, false));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final ConnectableFlowable<T> publish() {
      return this.publish(bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final ConnectableFlowable<T> publish(int var1) {
      ObjectHelper.verifyPositive(var1, "bufferSize");
      return FlowablePublish.create(this, var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> rebatchRequests(int var1) {
      return this.observeOn(ImmediateThinScheduler.INSTANCE, true, var1);
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> reduce(BiFunction<T, T, T> var1) {
      ObjectHelper.requireNonNull(var1, "reducer is null");
      return RxJavaPlugins.onAssembly(new FlowableReduceMaybe(this, var1));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Single<R> reduce(R var1, BiFunction<R, ? super T, R> var2) {
      ObjectHelper.requireNonNull(var1, "seed is null");
      ObjectHelper.requireNonNull(var2, "reducer is null");
      return RxJavaPlugins.onAssembly(new FlowableReduceSeedSingle(this, var1, var2));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Single<R> reduceWith(Callable<R> var1, BiFunction<R, ? super T, R> var2) {
      ObjectHelper.requireNonNull(var1, "seedSupplier is null");
      ObjectHelper.requireNonNull(var2, "reducer is null");
      return RxJavaPlugins.onAssembly(new FlowableReduceWithSingle(this, var1, var2));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> repeat() {
      return this.repeat(Long.MAX_VALUE);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> repeat(long var1) {
      if(var1 < 0L) {
         StringBuilder var3 = new StringBuilder();
         var3.append("times >= 0 required but it was ");
         var3.append(var1);
         throw new IllegalArgumentException(var3.toString());
      } else {
         return var1 == 0L?empty():RxJavaPlugins.onAssembly(new FlowableRepeat(this, var1));
      }
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> repeatUntil(BooleanSupplier var1) {
      ObjectHelper.requireNonNull(var1, "stop is null");
      return RxJavaPlugins.onAssembly(new FlowableRepeatUntil(this, var1));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> repeatWhen(Function<? super Flowable<Object>, ? extends Publisher<?>> var1) {
      ObjectHelper.requireNonNull(var1, "handler is null");
      return RxJavaPlugins.onAssembly(new FlowableRepeatWhen(this, var1));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> replay(Function<? super Flowable<T>, ? extends Publisher<R>> var1) {
      ObjectHelper.requireNonNull(var1, "selector is null");
      return FlowableReplay.multicastSelector(FlowableInternalHelper.replayCallable(this), var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> replay(Function<? super Flowable<T>, ? extends Publisher<R>> var1, int var2) {
      ObjectHelper.requireNonNull(var1, "selector is null");
      ObjectHelper.verifyPositive(var2, "bufferSize");
      return FlowableReplay.multicastSelector(FlowableInternalHelper.replayCallable(this, var2), var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final <R extends Object> Flowable<R> replay(Function<? super Flowable<T>, ? extends Publisher<R>> var1, int var2, long var3, TimeUnit var5) {
      return this.replay(var1, var2, var3, var5, Schedulers.computation());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final <R extends Object> Flowable<R> replay(Function<? super Flowable<T>, ? extends Publisher<R>> var1, int var2, long var3, TimeUnit var5, Scheduler var6) {
      ObjectHelper.requireNonNull(var1, "selector is null");
      ObjectHelper.requireNonNull(var5, "unit is null");
      ObjectHelper.verifyPositive(var2, "bufferSize");
      ObjectHelper.requireNonNull(var6, "scheduler is null");
      return FlowableReplay.multicastSelector(FlowableInternalHelper.replayCallable(this, var2, var3, var5, var6), var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final <R extends Object> Flowable<R> replay(Function<? super Flowable<T>, ? extends Publisher<R>> var1, int var2, Scheduler var3) {
      ObjectHelper.requireNonNull(var1, "selector is null");
      ObjectHelper.requireNonNull(var3, "scheduler is null");
      ObjectHelper.verifyPositive(var2, "bufferSize");
      return FlowableReplay.multicastSelector(FlowableInternalHelper.replayCallable(this, var2), FlowableInternalHelper.replayFunction(var1, var3));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final <R extends Object> Flowable<R> replay(Function<? super Flowable<T>, ? extends Publisher<R>> var1, long var2, TimeUnit var4) {
      return this.replay(var1, var2, var4, Schedulers.computation());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final <R extends Object> Flowable<R> replay(Function<? super Flowable<T>, ? extends Publisher<R>> var1, long var2, TimeUnit var4, Scheduler var5) {
      ObjectHelper.requireNonNull(var1, "selector is null");
      ObjectHelper.requireNonNull(var4, "unit is null");
      ObjectHelper.requireNonNull(var5, "scheduler is null");
      return FlowableReplay.multicastSelector(FlowableInternalHelper.replayCallable(this, var2, var4, var5), var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final <R extends Object> Flowable<R> replay(Function<? super Flowable<T>, ? extends Publisher<R>> var1, Scheduler var2) {
      ObjectHelper.requireNonNull(var1, "selector is null");
      ObjectHelper.requireNonNull(var2, "scheduler is null");
      return FlowableReplay.multicastSelector(FlowableInternalHelper.replayCallable(this), FlowableInternalHelper.replayFunction(var1, var2));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final ConnectableFlowable<T> replay() {
      return FlowableReplay.createFrom(this);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final ConnectableFlowable<T> replay(int var1) {
      ObjectHelper.verifyPositive(var1, "bufferSize");
      return FlowableReplay.create(this, var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final ConnectableFlowable<T> replay(int var1, long var2, TimeUnit var4) {
      return this.replay(var1, var2, var4, Schedulers.computation());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final ConnectableFlowable<T> replay(int var1, long var2, TimeUnit var4, Scheduler var5) {
      ObjectHelper.verifyPositive(var1, "bufferSize");
      ObjectHelper.requireNonNull(var4, "unit is null");
      ObjectHelper.requireNonNull(var5, "scheduler is null");
      ObjectHelper.verifyPositive(var1, "bufferSize");
      return FlowableReplay.create(this, var2, var4, var5, var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final ConnectableFlowable<T> replay(int var1, Scheduler var2) {
      ObjectHelper.requireNonNull(var2, "scheduler is null");
      return FlowableReplay.observeOn(this.replay(var1), var2);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final ConnectableFlowable<T> replay(long var1, TimeUnit var3) {
      return this.replay(var1, var3, Schedulers.computation());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final ConnectableFlowable<T> replay(long var1, TimeUnit var3, Scheduler var4) {
      ObjectHelper.requireNonNull(var3, "unit is null");
      ObjectHelper.requireNonNull(var4, "scheduler is null");
      return FlowableReplay.create(this, var1, var3, var4);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final ConnectableFlowable<T> replay(Scheduler var1) {
      ObjectHelper.requireNonNull(var1, "scheduler is null");
      return FlowableReplay.observeOn(this.replay(), var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> retry() {
      return this.retry(Long.MAX_VALUE, Functions.alwaysTrue());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> retry(long var1) {
      return this.retry(var1, Functions.alwaysTrue());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> retry(long var1, Predicate<? super Throwable> var3) {
      if(var1 < 0L) {
         StringBuilder var4 = new StringBuilder();
         var4.append("times >= 0 required but it was ");
         var4.append(var1);
         throw new IllegalArgumentException(var4.toString());
      } else {
         ObjectHelper.requireNonNull(var3, "predicate is null");
         return RxJavaPlugins.onAssembly(new FlowableRetryPredicate(this, var1, var3));
      }
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> retry(BiPredicate<? super Integer, ? super Throwable> var1) {
      ObjectHelper.requireNonNull(var1, "predicate is null");
      return RxJavaPlugins.onAssembly(new FlowableRetryBiPredicate(this, var1));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> retry(Predicate<? super Throwable> var1) {
      return this.retry(Long.MAX_VALUE, var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> retryUntil(BooleanSupplier var1) {
      ObjectHelper.requireNonNull(var1, "stop is null");
      return this.retry(Long.MAX_VALUE, Functions.predicateReverseFor(var1));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> retryWhen(Function<? super Flowable<Throwable>, ? extends Publisher<?>> var1) {
      ObjectHelper.requireNonNull(var1, "handler is null");
      return RxJavaPlugins.onAssembly(new FlowableRetryWhen(this, var1));
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @SchedulerSupport("none")
   public final void safeSubscribe(Subscriber<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "s is null");
      if(var1 instanceof SafeSubscriber) {
         this.subscribe((FlowableSubscriber)((SafeSubscriber)var1));
      } else {
         this.subscribe((FlowableSubscriber)(new SafeSubscriber(var1)));
      }
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Flowable<T> sample(long var1, TimeUnit var3) {
      return this.sample(var1, var3, Schedulers.computation());
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<T> sample(long var1, TimeUnit var3, Scheduler var4) {
      ObjectHelper.requireNonNull(var3, "unit is null");
      ObjectHelper.requireNonNull(var4, "scheduler is null");
      return RxJavaPlugins.onAssembly(new FlowableSampleTimed(this, var1, var3, var4, false));
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<T> sample(long var1, TimeUnit var3, Scheduler var4, boolean var5) {
      ObjectHelper.requireNonNull(var3, "unit is null");
      ObjectHelper.requireNonNull(var4, "scheduler is null");
      return RxJavaPlugins.onAssembly(new FlowableSampleTimed(this, var1, var3, var4, var5));
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Flowable<T> sample(long var1, TimeUnit var3, boolean var4) {
      return this.sample(var1, var3, Schedulers.computation(), var4);
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Flowable<T> sample(Publisher<U> var1) {
      ObjectHelper.requireNonNull(var1, "sampler is null");
      return RxJavaPlugins.onAssembly(new FlowableSamplePublisher(this, var1, false));
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Flowable<T> sample(Publisher<U> var1, boolean var2) {
      ObjectHelper.requireNonNull(var1, "sampler is null");
      return RxJavaPlugins.onAssembly(new FlowableSamplePublisher(this, var1, var2));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> scan(BiFunction<T, T, T> var1) {
      ObjectHelper.requireNonNull(var1, "accumulator is null");
      return RxJavaPlugins.onAssembly(new FlowableScan(this, var1));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> scan(R var1, BiFunction<R, ? super T, R> var2) {
      ObjectHelper.requireNonNull(var1, "seed is null");
      return this.scanWith(Functions.justCallable(var1), var2);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> scanWith(Callable<R> var1, BiFunction<R, ? super T, R> var2) {
      ObjectHelper.requireNonNull(var1, "seedSupplier is null");
      ObjectHelper.requireNonNull(var2, "accumulator is null");
      return RxJavaPlugins.onAssembly(new FlowableScanSeed(this, var1, var2));
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> serialize() {
      return RxJavaPlugins.onAssembly(new FlowableSerialized(this));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> share() {
      return this.publish().refCount();
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> single(T var1) {
      ObjectHelper.requireNonNull(var1, "defaultItem is null");
      return RxJavaPlugins.onAssembly(new FlowableSingleSingle(this, var1));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> singleElement() {
      return RxJavaPlugins.onAssembly(new FlowableSingleMaybe(this));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> singleOrError() {
      return RxJavaPlugins.onAssembly(new FlowableSingleSingle(this, (Object)null));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> skip(long var1) {
      return var1 <= 0L?RxJavaPlugins.onAssembly(this):RxJavaPlugins.onAssembly(new FlowableSkip(this, var1));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> skip(long var1, TimeUnit var3) {
      return this.skipUntil(timer(var1, var3));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<T> skip(long var1, TimeUnit var3, Scheduler var4) {
      return this.skipUntil(timer(var1, var3, var4));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> skipLast(int var1) {
      if(var1 < 0) {
         StringBuilder var2 = new StringBuilder();
         var2.append("count >= 0 required but it was ");
         var2.append(var1);
         throw new IndexOutOfBoundsException(var2.toString());
      } else {
         return var1 == 0?RxJavaPlugins.onAssembly(this):RxJavaPlugins.onAssembly(new FlowableSkipLast(this, var1));
      }
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> skipLast(long var1, TimeUnit var3) {
      return this.skipLast(var1, var3, Schedulers.computation(), false, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<T> skipLast(long var1, TimeUnit var3, Scheduler var4) {
      return this.skipLast(var1, var3, var4, false, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<T> skipLast(long var1, TimeUnit var3, Scheduler var4, boolean var5) {
      return this.skipLast(var1, var3, var4, var5, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<T> skipLast(long var1, TimeUnit var3, Scheduler var4, boolean var5, int var6) {
      ObjectHelper.requireNonNull(var3, "unit is null");
      ObjectHelper.requireNonNull(var4, "scheduler is null");
      ObjectHelper.verifyPositive(var6, "bufferSize");
      return RxJavaPlugins.onAssembly(new FlowableSkipLastTimed(this, var1, var3, var4, var6 << 1, var5));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> skipLast(long var1, TimeUnit var3, boolean var4) {
      return this.skipLast(var1, var3, Schedulers.computation(), var4, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Flowable<T> skipUntil(Publisher<U> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return RxJavaPlugins.onAssembly(new FlowableSkipUntil(this, var1));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> skipWhile(Predicate<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "predicate is null");
      return RxJavaPlugins.onAssembly(new FlowableSkipWhile(this, var1));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> sorted() {
      return this.toList().toFlowable().map(Functions.listSorter(Functions.naturalComparator())).flatMapIterable(Functions.identity());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> sorted(Comparator<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "sortFunction");
      return this.toList().toFlowable().map(Functions.listSorter(var1)).flatMapIterable(Functions.identity());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> startWith(Iterable<? extends T> var1) {
      return concatArray(new Publisher[]{fromIterable(var1), this});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> startWith(T var1) {
      ObjectHelper.requireNonNull(var1, "item is null");
      return concatArray(new Publisher[]{just(var1), this});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> startWith(Publisher<? extends T> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return concatArray(new Publisher[]{var1, this});
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> startWithArray(T ... var1) {
      Flowable var2 = fromArray(var1);
      return var2 == empty()?RxJavaPlugins.onAssembly(this):concatArray(new Publisher[]{var2, this});
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @SchedulerSupport("none")
   public final Disposable subscribe() {
      return this.subscribe(Functions.emptyConsumer(), Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION, RequestMax.INSTANCE);
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Disposable subscribe(Consumer<? super T> var1) {
      return this.subscribe(var1, Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION, RequestMax.INSTANCE);
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Disposable subscribe(Consumer<? super T> var1, Consumer<? super Throwable> var2) {
      return this.subscribe(var1, var2, Functions.EMPTY_ACTION, RequestMax.INSTANCE);
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Disposable subscribe(Consumer<? super T> var1, Consumer<? super Throwable> var2, Action var3) {
      return this.subscribe(var1, var2, var3, RequestMax.INSTANCE);
   }

   @BackpressureSupport(BackpressureKind.SPECIAL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Disposable subscribe(Consumer<? super T> var1, Consumer<? super Throwable> var2, Action var3, Consumer<? super Subscription> var4) {
      ObjectHelper.requireNonNull(var1, "onNext is null");
      ObjectHelper.requireNonNull(var2, "onError is null");
      ObjectHelper.requireNonNull(var3, "onComplete is null");
      ObjectHelper.requireNonNull(var4, "onSubscribe is null");
      LambdaSubscriber var5 = new LambdaSubscriber(var1, var2, var3, var4);
      this.subscribe((FlowableSubscriber)var5);
      return var5;
   }

   @BackpressureSupport(BackpressureKind.SPECIAL)
   @SchedulerSupport("none")
   @Beta
   public final void subscribe(FlowableSubscriber<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "s is null");

      try {
         Subscriber var5 = RxJavaPlugins.onSubscribe(this, var1);
         ObjectHelper.requireNonNull(var5, "Plugin returned null Subscriber");
         this.subscribeActual(var5);
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

   @BackpressureSupport(BackpressureKind.SPECIAL)
   @SchedulerSupport("none")
   public final void subscribe(Subscriber<? super T> var1) {
      if(var1 instanceof FlowableSubscriber) {
         this.subscribe((FlowableSubscriber)var1);
      } else {
         ObjectHelper.requireNonNull(var1, "s is null");
         this.subscribe((FlowableSubscriber)(new StrictSubscriber(var1)));
      }
   }

   public abstract void subscribeActual(Subscriber<? super T> var1);

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<T> subscribeOn(@NonNull Scheduler var1) {
      ObjectHelper.requireNonNull(var1, "scheduler is null");
      boolean var2;
      if(!(this instanceof FlowableCreate)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return this.subscribeOn(var1, var2);
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("custom")
   @Experimental
   public final Flowable<T> subscribeOn(@NonNull Scheduler var1, boolean var2) {
      ObjectHelper.requireNonNull(var1, "scheduler is null");
      return RxJavaPlugins.onAssembly(new FlowableSubscribeOn(this, var1, var2));
   }

   @BackpressureSupport(BackpressureKind.SPECIAL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <E extends Object & Subscriber<? super T>> E subscribeWith(E var1) {
      this.subscribe(var1);
      return var1;
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> switchIfEmpty(Publisher<? extends T> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return RxJavaPlugins.onAssembly(new FlowableSwitchIfEmpty(this, var1));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> switchMap(Function<? super T, ? extends Publisher<? extends R>> var1) {
      return this.switchMap(var1, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> switchMap(Function<? super T, ? extends Publisher<? extends R>> var1, int var2) {
      return this.switchMap0(var1, var2, false);
   }

   <R extends Object> Flowable<R> switchMap0(Function<? super T, ? extends Publisher<? extends R>> var1, int var2, boolean var3) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.verifyPositive(var2, "bufferSize");
      if(this instanceof ScalarCallable) {
         Object var4 = ((ScalarCallable)this).call();
         return var4 == null?empty():FlowableScalarXMap.scalarXMap(var4, var1);
      } else {
         return RxJavaPlugins.onAssembly(new FlowableSwitchMap(this, var1, var2, var3));
      }
   }

   @BackpressureSupport(BackpressureKind.SPECIAL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> switchMapDelayError(Function<? super T, ? extends Publisher<? extends R>> var1) {
      return this.switchMapDelayError(var1, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.SPECIAL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> switchMapDelayError(Function<? super T, ? extends Publisher<? extends R>> var1, int var2) {
      return this.switchMap0(var1, var2, true);
   }

   @BackpressureSupport(BackpressureKind.SPECIAL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> take(long var1) {
      if(var1 < 0L) {
         StringBuilder var3 = new StringBuilder();
         var3.append("count >= 0 required but it was ");
         var3.append(var1);
         throw new IllegalArgumentException(var3.toString());
      } else {
         return RxJavaPlugins.onAssembly(new FlowableTake(this, var1));
      }
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Flowable<T> take(long var1, TimeUnit var3) {
      return this.takeUntil((Publisher)timer(var1, var3));
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<T> take(long var1, TimeUnit var3, Scheduler var4) {
      return this.takeUntil((Publisher)timer(var1, var3, var4));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> takeLast(int var1) {
      if(var1 < 0) {
         StringBuilder var2 = new StringBuilder();
         var2.append("count >= 0 required but it was ");
         var2.append(var1);
         throw new IndexOutOfBoundsException(var2.toString());
      } else {
         return var1 == 0?RxJavaPlugins.onAssembly(new FlowableIgnoreElements(this)):(var1 == 1?RxJavaPlugins.onAssembly(new FlowableTakeLastOne(this)):RxJavaPlugins.onAssembly(new FlowableTakeLast(this, var1)));
      }
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> takeLast(long var1, long var3, TimeUnit var5) {
      return this.takeLast(var1, var3, var5, Schedulers.computation(), false, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<T> takeLast(long var1, long var3, TimeUnit var5, Scheduler var6) {
      return this.takeLast(var1, var3, var5, var6, false, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<T> takeLast(long var1, long var3, TimeUnit var5, Scheduler var6, boolean var7, int var8) {
      ObjectHelper.requireNonNull(var5, "unit is null");
      ObjectHelper.requireNonNull(var6, "scheduler is null");
      ObjectHelper.verifyPositive(var8, "bufferSize");
      if(var1 < 0L) {
         StringBuilder var9 = new StringBuilder();
         var9.append("count >= 0 required but it was ");
         var9.append(var1);
         throw new IndexOutOfBoundsException(var9.toString());
      } else {
         return RxJavaPlugins.onAssembly(new FlowableTakeLastTimed(this, var1, var3, var5, var6, var8, var7));
      }
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Flowable<T> takeLast(long var1, TimeUnit var3) {
      return this.takeLast(var1, var3, Schedulers.computation(), false, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<T> takeLast(long var1, TimeUnit var3, Scheduler var4) {
      return this.takeLast(var1, var3, var4, false, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<T> takeLast(long var1, TimeUnit var3, Scheduler var4, boolean var5) {
      return this.takeLast(var1, var3, var4, var5, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<T> takeLast(long var1, TimeUnit var3, Scheduler var4, boolean var5, int var6) {
      return this.takeLast(Long.MAX_VALUE, var1, var3, var4, var5, var6);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Flowable<T> takeLast(long var1, TimeUnit var3, boolean var4) {
      return this.takeLast(var1, var3, Schedulers.computation(), var4, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> takeUntil(Predicate<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "stopPredicate is null");
      return RxJavaPlugins.onAssembly(new FlowableTakeUntilPredicate(this, var1));
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object> Flowable<T> takeUntil(Publisher<U> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return RxJavaPlugins.onAssembly(new FlowableTakeUntil(this, var1));
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> takeWhile(Predicate<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "predicate is null");
      return RxJavaPlugins.onAssembly(new FlowableTakeWhile(this, var1));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final TestSubscriber<T> test() {
      TestSubscriber var1 = new TestSubscriber();
      this.subscribe((FlowableSubscriber)var1);
      return var1;
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final TestSubscriber<T> test(long var1) {
      TestSubscriber var3 = new TestSubscriber(var1);
      this.subscribe((FlowableSubscriber)var3);
      return var3;
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final TestSubscriber<T> test(long var1, boolean var3) {
      TestSubscriber var4 = new TestSubscriber(var1);
      if(var3) {
         var4.cancel();
      }

      this.subscribe((FlowableSubscriber)var4);
      return var4;
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Flowable<T> throttleFirst(long var1, TimeUnit var3) {
      return this.throttleFirst(var1, var3, Schedulers.computation());
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<T> throttleFirst(long var1, TimeUnit var3, Scheduler var4) {
      ObjectHelper.requireNonNull(var3, "unit is null");
      ObjectHelper.requireNonNull(var4, "scheduler is null");
      return RxJavaPlugins.onAssembly(new FlowableThrottleFirstTimed(this, var1, var3, var4));
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Flowable<T> throttleLast(long var1, TimeUnit var3) {
      return this.sample(var1, var3);
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<T> throttleLast(long var1, TimeUnit var3, Scheduler var4) {
      return this.sample(var1, var3, var4);
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Flowable<T> throttleWithTimeout(long var1, TimeUnit var3) {
      return this.debounce(var1, var3);
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<T> throttleWithTimeout(long var1, TimeUnit var3, Scheduler var4) {
      return this.debounce(var1, var3, var4);
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<Timed<T>> timeInterval() {
      return this.timeInterval(TimeUnit.MILLISECONDS, Schedulers.computation());
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<Timed<T>> timeInterval(Scheduler var1) {
      return this.timeInterval(TimeUnit.MILLISECONDS, var1);
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<Timed<T>> timeInterval(TimeUnit var1) {
      return this.timeInterval(var1, Schedulers.computation());
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<Timed<T>> timeInterval(TimeUnit var1, Scheduler var2) {
      ObjectHelper.requireNonNull(var1, "unit is null");
      ObjectHelper.requireNonNull(var2, "scheduler is null");
      return RxJavaPlugins.onAssembly(new FlowableTimeInterval(this, var1, var2));
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Flowable<T> timeout(long var1, TimeUnit var3) {
      return this.timeout0(var1, var3, (Publisher)null, Schedulers.computation());
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<T> timeout(long var1, TimeUnit var3, Scheduler var4) {
      return this.timeout0(var1, var3, (Publisher)null, var4);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<T> timeout(long var1, TimeUnit var3, Scheduler var4, Publisher<? extends T> var5) {
      ObjectHelper.requireNonNull(var5, "other is null");
      return this.timeout0(var1, var3, var5, var4);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Flowable<T> timeout(long var1, TimeUnit var3, Publisher<? extends T> var4) {
      ObjectHelper.requireNonNull(var4, "other is null");
      return this.timeout0(var1, var3, var4, Schedulers.computation());
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <V extends Object> Flowable<T> timeout(Function<? super T, ? extends Publisher<V>> var1) {
      return this.timeout0((Publisher)null, var1, (Publisher)null);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <V extends Object> Flowable<T> timeout(Function<? super T, ? extends Publisher<V>> var1, Flowable<? extends T> var2) {
      ObjectHelper.requireNonNull(var2, "other is null");
      return this.timeout0((Publisher)null, var1, var2);
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, V extends Object> Flowable<T> timeout(Publisher<U> var1, Function<? super T, ? extends Publisher<V>> var2) {
      ObjectHelper.requireNonNull(var1, "firstTimeoutIndicator is null");
      return this.timeout0(var1, var2, (Publisher)null);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, V extends Object> Flowable<T> timeout(Publisher<U> var1, Function<? super T, ? extends Publisher<V>> var2, Publisher<? extends T> var3) {
      ObjectHelper.requireNonNull(var1, "firstTimeoutSelector is null");
      ObjectHelper.requireNonNull(var3, "other is null");
      return this.timeout0(var1, var2, var3);
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<Timed<T>> timestamp() {
      return this.timestamp(TimeUnit.MILLISECONDS, Schedulers.computation());
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<Timed<T>> timestamp(Scheduler var1) {
      return this.timestamp(TimeUnit.MILLISECONDS, var1);
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<Timed<T>> timestamp(TimeUnit var1) {
      return this.timestamp(var1, Schedulers.computation());
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<Timed<T>> timestamp(TimeUnit var1, Scheduler var2) {
      ObjectHelper.requireNonNull(var1, "unit is null");
      ObjectHelper.requireNonNull(var2, "scheduler is null");
      return this.map(Functions.timestampWith(var1, var2));
   }

   @BackpressureSupport(BackpressureKind.SPECIAL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> R to(Function<? super Flowable<T>, R> var1) {
      try {
         Object var3 = ((Function)ObjectHelper.requireNonNull(var1, "converter is null")).apply(this);
         return var3;
      } catch (Throwable var2) {
         Exceptions.throwIfFatal(var2);
         throw ExceptionHelper.wrapOrThrow(var2);
      }
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Future<T> toFuture() {
      return (Future)this.subscribeWith(new FutureSubscriber());
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<List<T>> toList() {
      return RxJavaPlugins.onAssembly(new FlowableToListSingle(this));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<List<T>> toList(int var1) {
      ObjectHelper.verifyPositive(var1, "capacityHint");
      return RxJavaPlugins.onAssembly(new FlowableToListSingle(this, Functions.createArrayList(var1)));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object & Collection<? super T>> Single<U> toList(Callable<U> var1) {
      ObjectHelper.requireNonNull(var1, "collectionSupplier is null");
      return RxJavaPlugins.onAssembly(new FlowableToListSingle(this, var1));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <K extends Object> Single<Map<K, T>> toMap(Function<? super T, ? extends K> var1) {
      ObjectHelper.requireNonNull(var1, "keySelector is null");
      return this.collect(HashMapSupplier.asCallable(), Functions.toMapKeySelector(var1));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <K extends Object, V extends Object> Single<Map<K, V>> toMap(Function<? super T, ? extends K> var1, Function<? super T, ? extends V> var2) {
      ObjectHelper.requireNonNull(var1, "keySelector is null");
      ObjectHelper.requireNonNull(var2, "valueSelector is null");
      return this.collect(HashMapSupplier.asCallable(), Functions.toMapKeyValueSelector(var1, var2));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <K extends Object, V extends Object> Single<Map<K, V>> toMap(Function<? super T, ? extends K> var1, Function<? super T, ? extends V> var2, Callable<? extends Map<K, V>> var3) {
      ObjectHelper.requireNonNull(var1, "keySelector is null");
      ObjectHelper.requireNonNull(var2, "valueSelector is null");
      return this.collect(var3, Functions.toMapKeyValueSelector(var1, var2));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <K extends Object> Single<Map<K, Collection<T>>> toMultimap(Function<? super T, ? extends K> var1) {
      return this.toMultimap(var1, Functions.identity(), HashMapSupplier.asCallable(), ArrayListSupplier.asFunction());
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <K extends Object, V extends Object> Single<Map<K, Collection<V>>> toMultimap(Function<? super T, ? extends K> var1, Function<? super T, ? extends V> var2) {
      return this.toMultimap(var1, var2, HashMapSupplier.asCallable(), ArrayListSupplier.asFunction());
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <K extends Object, V extends Object> Single<Map<K, Collection<V>>> toMultimap(Function<? super T, ? extends K> var1, Function<? super T, ? extends V> var2, Callable<Map<K, Collection<V>>> var3) {
      return this.toMultimap(var1, var2, var3, ArrayListSupplier.asFunction());
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <K extends Object, V extends Object> Single<Map<K, Collection<V>>> toMultimap(Function<? super T, ? extends K> var1, Function<? super T, ? extends V> var2, Callable<? extends Map<K, Collection<V>>> var3, Function<? super K, ? extends Collection<? super V>> var4) {
      ObjectHelper.requireNonNull(var1, "keySelector is null");
      ObjectHelper.requireNonNull(var2, "valueSelector is null");
      ObjectHelper.requireNonNull(var3, "mapSupplier is null");
      ObjectHelper.requireNonNull(var4, "collectionFactory is null");
      return this.collect(var3, Functions.toMultimapKeyValueSelector(var1, var2, var4));
   }

   @BackpressureSupport(BackpressureKind.NONE)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> toObservable() {
      return RxJavaPlugins.onAssembly(new ObservableFromPublisher(this));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<List<T>> toSortedList() {
      return this.toSortedList(Functions.naturalComparator());
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<List<T>> toSortedList(int var1) {
      return this.toSortedList(Functions.naturalComparator(), var1);
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<List<T>> toSortedList(Comparator<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "comparator is null");
      return this.toList().map(Functions.listSorter(var1));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<List<T>> toSortedList(Comparator<? super T> var1, int var2) {
      ObjectHelper.requireNonNull(var1, "comparator is null");
      return this.toList(var2).map(Functions.listSorter(var1));
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<T> unsubscribeOn(Scheduler var1) {
      ObjectHelper.requireNonNull(var1, "scheduler is null");
      return RxJavaPlugins.onAssembly(new FlowableUnsubscribeOn(this, var1));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<Flowable<T>> window(long var1) {
      return this.window(var1, var1, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<Flowable<T>> window(long var1, long var3) {
      return this.window(var1, var3, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<Flowable<T>> window(long var1, long var3, int var5) {
      ObjectHelper.verifyPositive(var3, "skip");
      ObjectHelper.verifyPositive(var1, "count");
      ObjectHelper.verifyPositive(var5, "bufferSize");
      return RxJavaPlugins.onAssembly(new FlowableWindow(this, var1, var3, var5));
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Flowable<Flowable<T>> window(long var1, long var3, TimeUnit var5) {
      return this.window(var1, var3, var5, Schedulers.computation(), bufferSize());
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<Flowable<T>> window(long var1, long var3, TimeUnit var5, Scheduler var6) {
      return this.window(var1, var3, var5, var6, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<Flowable<T>> window(long var1, long var3, TimeUnit var5, Scheduler var6, int var7) {
      ObjectHelper.verifyPositive(var7, "bufferSize");
      ObjectHelper.verifyPositive(var1, "timespan");
      ObjectHelper.verifyPositive(var3, "timeskip");
      ObjectHelper.requireNonNull(var6, "scheduler is null");
      ObjectHelper.requireNonNull(var5, "unit is null");
      return RxJavaPlugins.onAssembly(new FlowableWindowTimed(this, var1, var3, var5, var6, Long.MAX_VALUE, var7, false));
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Flowable<Flowable<T>> window(long var1, TimeUnit var3) {
      return this.window(var1, var3, Schedulers.computation(), Long.MAX_VALUE, false);
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Flowable<Flowable<T>> window(long var1, TimeUnit var3, long var4) {
      return this.window(var1, var3, Schedulers.computation(), var4, false);
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Flowable<Flowable<T>> window(long var1, TimeUnit var3, long var4, boolean var6) {
      return this.window(var1, var3, Schedulers.computation(), var4, var6);
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<Flowable<T>> window(long var1, TimeUnit var3, Scheduler var4) {
      return this.window(var1, var3, var4, Long.MAX_VALUE, false);
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<Flowable<T>> window(long var1, TimeUnit var3, Scheduler var4, long var5) {
      return this.window(var1, var3, var4, var5, false);
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<Flowable<T>> window(long var1, TimeUnit var3, Scheduler var4, long var5, boolean var7) {
      return this.window(var1, var3, var4, var5, var7, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<Flowable<T>> window(long var1, TimeUnit var3, Scheduler var4, long var5, boolean var7, int var8) {
      ObjectHelper.verifyPositive(var8, "bufferSize");
      ObjectHelper.requireNonNull(var4, "scheduler is null");
      ObjectHelper.requireNonNull(var3, "unit is null");
      ObjectHelper.verifyPositive(var5, "count");
      return RxJavaPlugins.onAssembly(new FlowableWindowTimed(this, var1, var1, var3, var4, var5, var8, var7));
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <B extends Object> Flowable<Flowable<T>> window(Callable<? extends Publisher<B>> var1) {
      return this.window(var1, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <B extends Object> Flowable<Flowable<T>> window(Callable<? extends Publisher<B>> var1, int var2) {
      ObjectHelper.requireNonNull(var1, "boundaryIndicatorSupplier is null");
      ObjectHelper.verifyPositive(var2, "bufferSize");
      return RxJavaPlugins.onAssembly(new FlowableWindowBoundarySupplier(this, var1, var2));
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <B extends Object> Flowable<Flowable<T>> window(Publisher<B> var1) {
      return this.window(var1, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <B extends Object> Flowable<Flowable<T>> window(Publisher<B> var1, int var2) {
      ObjectHelper.requireNonNull(var1, "boundaryIndicator is null");
      ObjectHelper.verifyPositive(var2, "bufferSize");
      return RxJavaPlugins.onAssembly(new FlowableWindowBoundary(this, var1, var2));
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, V extends Object> Flowable<Flowable<T>> window(Publisher<U> var1, Function<? super U, ? extends Publisher<V>> var2) {
      return this.window(var1, var2, bufferSize());
   }

   @BackpressureSupport(BackpressureKind.ERROR)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, V extends Object> Flowable<Flowable<T>> window(Publisher<U> var1, Function<? super U, ? extends Publisher<V>> var2, int var3) {
      ObjectHelper.requireNonNull(var1, "openingIndicator is null");
      ObjectHelper.requireNonNull(var2, "closingIndicator is null");
      ObjectHelper.verifyPositive(var3, "bufferSize");
      return RxJavaPlugins.onAssembly(new FlowableWindowBoundarySelector(this, var1, var2, var3));
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> withLatestFrom(Iterable<? extends Publisher<?>> var1, Function<? super Object[], R> var2) {
      ObjectHelper.requireNonNull(var1, "others is null");
      ObjectHelper.requireNonNull(var2, "combiner is null");
      return RxJavaPlugins.onAssembly(new FlowableWithLatestFromMany(this, var1, var2));
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, R extends Object> Flowable<R> withLatestFrom(Publisher<? extends U> var1, BiFunction<? super T, ? super U, ? extends R> var2) {
      ObjectHelper.requireNonNull(var1, "other is null");
      ObjectHelper.requireNonNull(var2, "combiner is null");
      return RxJavaPlugins.onAssembly(new FlowableWithLatestFrom(this, var2, var1));
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T1 extends Object, T2 extends Object, R extends Object> Flowable<R> withLatestFrom(Publisher<T1> var1, Publisher<T2> var2, Function3<? super T, ? super T1, ? super T2, R> var3) {
      ObjectHelper.requireNonNull(var1, "source1 is null");
      ObjectHelper.requireNonNull(var2, "source2 is null");
      Function var4 = Functions.toFunction(var3);
      return this.withLatestFrom(new Publisher[]{var1, var2}, var4);
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T1 extends Object, T2 extends Object, T3 extends Object, R extends Object> Flowable<R> withLatestFrom(Publisher<T1> var1, Publisher<T2> var2, Publisher<T3> var3, Function4<? super T, ? super T1, ? super T2, ? super T3, R> var4) {
      ObjectHelper.requireNonNull(var1, "source1 is null");
      ObjectHelper.requireNonNull(var2, "source2 is null");
      ObjectHelper.requireNonNull(var3, "source3 is null");
      Function var5 = Functions.toFunction(var4);
      return this.withLatestFrom(new Publisher[]{var1, var2, var3}, var5);
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T1 extends Object, T2 extends Object, T3 extends Object, T4 extends Object, R extends Object> Flowable<R> withLatestFrom(Publisher<T1> var1, Publisher<T2> var2, Publisher<T3> var3, Publisher<T4> var4, Function5<? super T, ? super T1, ? super T2, ? super T3, ? super T4, R> var5) {
      ObjectHelper.requireNonNull(var1, "source1 is null");
      ObjectHelper.requireNonNull(var2, "source2 is null");
      ObjectHelper.requireNonNull(var3, "source3 is null");
      ObjectHelper.requireNonNull(var4, "source4 is null");
      Function var6 = Functions.toFunction(var5);
      return this.withLatestFrom(new Publisher[]{var1, var2, var3, var4}, var6);
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R extends Object> Flowable<R> withLatestFrom(Publisher<?>[] var1, Function<? super Object[], R> var2) {
      ObjectHelper.requireNonNull(var1, "others is null");
      ObjectHelper.requireNonNull(var2, "combiner is null");
      return RxJavaPlugins.onAssembly(new FlowableWithLatestFromMany(this, var1, var2));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, R extends Object> Flowable<R> zipWith(Iterable<U> var1, BiFunction<? super T, ? super U, ? extends R> var2) {
      ObjectHelper.requireNonNull(var1, "other is null");
      ObjectHelper.requireNonNull(var2, "zipper is null");
      return RxJavaPlugins.onAssembly(new FlowableZipIterable(this, var1, var2));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, R extends Object> Flowable<R> zipWith(Publisher<? extends U> var1, BiFunction<? super T, ? super U, ? extends R> var2) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return zip(this, var1, var2);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, R extends Object> Flowable<R> zipWith(Publisher<? extends U> var1, BiFunction<? super T, ? super U, ? extends R> var2, boolean var3) {
      return zip(this, var1, var2, var3);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U extends Object, R extends Object> Flowable<R> zipWith(Publisher<? extends U> var1, BiFunction<? super T, ? super U, ? extends R> var2, boolean var3, int var4) {
      return zip(this, var1, var2, var3, var4);
   }
}
