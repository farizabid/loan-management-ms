package loan.management.utils;

import java.time.LocalDateTime;

public class ObjectActiveAndCreatedDateUtil {
    public ObjectActiveAndCreatedDateUtil() {
    }

    public static void registerObject(CommonObjectActiveAndCreatedDate obj) {
        registerObject(obj, 0L);
    }

    public static void registerObject(CommonObjectActiveAndCreatedDate obj, long userId) {
        obj.setActiveDatetime(LocalDateTime.now());
        obj.setIsactive(true);
        obj.setVersion(0L);
        obj.setCreatedBy(userId);
        obj.setCreatedDatetime(LocalDateTime.now());
        obj.setUpdatedBy(userId);
        obj.setUpdatedDatetime(LocalDateTime.now());
    }

    public static void updateObject(CommonObjectActiveAndCreatedDate obj, boolean isactiveBefore) {
        updateObject(obj, 0L, isactiveBefore);
    }

    public static void updateObject(CommonObjectActiveAndCreatedDate obj, long userId, boolean currentActiveStatus) {
        if (currentActiveStatus != obj.isIsactive()) {
            if (currentActiveStatus) {
                obj.setActiveDatetime(LocalDateTime.now());
                obj.setInactiveDatetime((LocalDateTime)null);
            } else {
                obj.setInactiveDatetime(LocalDateTime.now());
            }
        }

        obj.setIsactive(currentActiveStatus);
        obj.setVersion(obj.getVersion() + 1L);
        obj.setUpdatedBy(userId);
        obj.setUpdatedDatetime(LocalDateTime.now());
    }
}
