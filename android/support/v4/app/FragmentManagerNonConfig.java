package android.support.v4.app;

import android.support.v4.app.Fragment;
import java.util.List;

public class FragmentManagerNonConfig {

   private final List<FragmentManagerNonConfig> mChildNonConfigs;
   private final List<Fragment> mFragments;
   private final List<o> mViewModelStores;


   FragmentManagerNonConfig(List<Fragment> var1, List<FragmentManagerNonConfig> var2, List<o> var3) {
      this.mFragments = var1;
      this.mChildNonConfigs = var2;
      this.mViewModelStores = var3;
   }

   List<FragmentManagerNonConfig> getChildNonConfigs() {
      return this.mChildNonConfigs;
   }

   List<Fragment> getFragments() {
      return this.mFragments;
   }

   List<o> getViewModelStores() {
      return this.mViewModelStores;
   }
}
