package com.facebook.litho;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface FrameworkLogEvents {

   int EVENT_CALCULATE_LAYOUT_STATE = 16;
   int EVENT_COLLECT_RESULTS = 2;
   int EVENT_CREATE_LAYOUT = 0;
   int EVENT_CSS_LAYOUT = 1;
   int EVENT_DRAW = 17;
   int EVENT_LAYOUT_CALCULATE = 3;
   int EVENT_MOUNT = 6;
   int EVENT_PREPARE_MOUNT = 5;
   int EVENT_PRE_ALLOCATE_MOUNT_CONTENT = 8;
   int EVENT_SECTIONS_CREATE_NEW_TREE = 11;
   int EVENT_SECTIONS_DATA_DIFF_CALCULATE_DIFF = 12;
   int EVENT_SECTIONS_GENERATE_CHANGESET = 13;
   int EVENT_SECTIONS_ON_CREATE_CHILDREN = 14;
   int EVENT_SECTIONS_SET_ROOT = 15;
   String PARAM_ATTRIBUTION = "attribution";
   String PARAM_CHANGESET_CHANGE_COUNT = "change_count";
   String PARAM_CHANGESET_DELETE_RANGE_COUNT = "changeset_delete_range_count";
   String PARAM_CHANGESET_DELETE_SINGLE_COUNT = "changeset_delete_single_count";
   String PARAM_CHANGESET_EFFECTIVE_COUNT = "changeset_effective_count";
   String PARAM_CHANGESET_FINAL_COUNT = "final_count";
   String PARAM_CHANGESET_INSERT_RANGE_COUNT = "changeset_insert_range_count";
   String PARAM_CHANGESET_INSERT_SINGLE_COUNT = "changeset_insert_single_count";
   String PARAM_CHANGESET_MOVE_COUNT = "changeset_move_count";
   String PARAM_CHANGESET_UPDATE_RANGE_COUNT = "changeset_update_range_count";
   String PARAM_CHANGESET_UPDATE_SINGLE_COUNT = "changeset_update_single_count";
   String PARAM_COMPONENT = "component";
   String PARAM_CURRENT_ROOT_COUNT = "current_root_count";
   String PARAM_DRAWN_CONTENT = "drawn_content";
   String PARAM_DRAWN_TIME = "drawn_time";
   String PARAM_IS_BACKGROUND_LAYOUT = "is_background_layout";
   String PARAM_IS_DIRTY = "is_dirty";
   String PARAM_LAYOUT_STATE_SOURCE = "calculate_layout_state_source";
   String PARAM_LOG_TAG = "log_tag";
   String PARAM_MOUNTED_CONTENT = "mounted_content";
   String PARAM_MOUNTED_COUNT = "mounted_count";
   String PARAM_MOUNTED_EXTRAS = "mounted_extras";
   String PARAM_MOUNTED_TIME = "mounted_time_ms";
   String PARAM_MOVED_COUNT = "moved_count";
   String PARAM_NO_OP_COUNT = "no_op_count";
   String PARAM_ROOT_COMPONENT = "root_component";
   String PARAM_SECTION_CURRENT = "section_current";
   String PARAM_SECTION_NEXT = "section_next";
   String PARAM_SECTION_SET_ROOT_SOURCE = "section_set_root_source";
   String PARAM_SET_ROOT_ON_BG_THREAD = "sections_set_root_bg_thread";
   String PARAM_TREE_DIFF_ENABLED = "tree_diff_enabled";
   String PARAM_UNCHANGED_COUNT = "unchanged_count";
   String PARAM_UNMOUNTED_CONTENT = "unmounted_content";
   String PARAM_UNMOUNTED_COUNT = "unmounted_count";
   String PARAM_UNMOUNTED_TIME = "unmounted_time_ms";
   String PARAM_UPDATED_CONTENT = "updated_content";
   String PARAM_UPDATED_COUNT = "updated_count";
   String PARAM_UPDATED_TIME = "updated_time_ms";
   String PARAM_VISIBILITY_HANDLER = "visibility_handler";
   String PARAM_VISIBILITY_HANDLERS_TOTAL_TIME = "visibility_handlers_total_time_ms";
   String PARAM_VISIBILITY_HANDLER_TIME = "visibility_handler_time_ms";


   @Retention(RetentionPolicy.SOURCE)
   public @interface LogEventId {
   }
}
