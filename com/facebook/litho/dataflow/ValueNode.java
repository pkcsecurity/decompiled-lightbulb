package com.facebook.litho.dataflow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Nullable;

public abstract class ValueNode {

   public static final String DEFAULT_INPUT = "default_input";
   private Map<String, ValueNode> mInputs = null;
   private ArrayList<ValueNode> mOutputs = null;
   private long mTimeNs = 0L;
   private float mValue;


   private String buildDebugInputsString() {
      if(this.mInputs == null) {
         return "[]";
      } else {
         String var1 = "";
         Iterator var3 = this.mInputs.keySet().iterator();

         StringBuilder var2;
         while(var3.hasNext()) {
            var2 = new StringBuilder();
            var2.append(var1);
            var2.append("\'");
            var2.append((String)var3.next());
            var2.append("\'");
            String var5 = var2.toString();
            var1 = var5;
            if(!var3.hasNext()) {
               StringBuilder var4 = new StringBuilder();
               var4.append(var5);
               var4.append(", ");
               var1 = var4.toString();
            }
         }

         var2 = new StringBuilder();
         var2.append("[");
         var2.append(var1);
         var2.append("]");
         return var2.toString();
      }
   }

   void addOutput(ValueNode var1) {
      if(this.mOutputs == null) {
         this.mOutputs = new ArrayList();
      }

      this.mOutputs.add(var1);
   }

   public abstract float calculateValue(long var1);

   final void doCalculateValue(long var1) {
      float var3 = this.calculateValue(var1);
      if(var1 == this.mTimeNs) {
         throw new RuntimeException("Got a calculate value call multiple times in the same frame. This isn\'t expected.");
      } else {
         this.mTimeNs = var1;
         this.mValue = var3;
      }
   }

   Collection<ValueNode> getAllInputs() {
      return (Collection)(this.mInputs == null?Collections.emptySet():this.mInputs.values());
   }

   protected ValueNode getInput() {
      if(this.getInputCount() > 1) {
         throw new RuntimeException("Trying to get single input of node with multiple inputs!");
      } else {
         return this.getInput("default_input");
      }
   }

   protected ValueNode getInput(String var1) {
      ValueNode var2 = this.getInputUnsafe(var1);
      if(var2 == null) {
         StringBuilder var3 = new StringBuilder();
         var3.append("Tried to get non-existent input \'");
         var3.append(var1);
         var3.append("\'. Node only has these inputs: ");
         var3.append(this.buildDebugInputsString());
         throw new RuntimeException(var3.toString());
      } else {
         return var2;
      }
   }

   int getInputCount() {
      return this.mInputs == null?0:this.mInputs.size();
   }

   @Nullable
   ValueNode getInputUnsafe(String var1) {
      return this.mInputs == null?null:(ValueNode)this.mInputs.get(var1);
   }

   ValueNode getOutput() {
      if(this.getOutputCount() != 1) {
         StringBuilder var1 = new StringBuilder();
         var1.append("Node does not have inputs of size 1: ");
         var1.append(this.getOutputCount());
         throw new RuntimeException(var1.toString());
      } else {
         return this.getOutputAt(0);
      }
   }

   ValueNode getOutputAt(int var1) {
      return (ValueNode)this.mOutputs.get(var1);
   }

   int getOutputCount() {
      return this.mOutputs == null?0:this.mOutputs.size();
   }

   public float getValue() {
      return this.mValue;
   }

   protected boolean hasInput() {
      if(this.getInputCount() > 1) {
         throw new RuntimeException("Trying to check for single input of node with multiple inputs!");
      } else {
         return this.hasInput("default_input");
      }
   }

   protected boolean hasInput(String var1) {
      return this.mInputs == null?false:this.mInputs.containsKey(var1);
   }

   void removeInput(String var1) {
      if(this.mInputs == null || this.mInputs.remove(var1) == null) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Tried to remove non-existent input with name: ");
         var2.append(var1);
         throw new RuntimeException(var2.toString());
      }
   }

   void removeOutput(ValueNode var1) {
      if(!this.mOutputs.remove(var1)) {
         throw new RuntimeException("Tried to remove non-existent input!");
      }
   }

   void setInput(String var1, ValueNode var2) {
      if(this.mInputs == null) {
         this.mInputs = new LinkedHashMap();
      }

      this.mInputs.put(var1, var2);
   }

   public void setValue(float var1) {
      this.mValue = var1;
   }
}
