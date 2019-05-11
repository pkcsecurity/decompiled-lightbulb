package bolts;

import bolts.Task;

public interface Continuation<TTaskResult extends Object, TContinuationResult extends Object> {

   TContinuationResult then(Task<TTaskResult> var1) throws Exception;
}
