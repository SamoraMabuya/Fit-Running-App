package mobile;

import android.provider.BaseColumns;

public class UserActivity {

    private UserActivity() {}

    public  static final class ActivityEntry {
        public static final String TABLE_NAME = "runora_user_table";
        public static final String COL_1 = "Id";
        public static final String COL_2 = "Elapsed_Time";
        public static final String COL_3 = "Total_Distance";
        public static final String ROW_1 = "Date";
    }
}
