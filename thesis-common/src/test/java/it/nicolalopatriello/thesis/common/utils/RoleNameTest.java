package it.nicolalopatriello.thesis.common.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("Role names")
class RoleNameTest {

    @Test
    void is_super_user() {
        assertFalse(RoleName.DEVELOPER.isSuperUser());
        assertFalse(RoleName.MAINTAINER.isSuperUser());
        assertFalse(RoleName.READ_ONLY.isSuperUser());
        assertFalse(RoleName.REPORT_READ_ONLY.isSuperUser());
        assertTrue(RoleName.ROLE_SYSTEM.isSuperUser());
    }

    @Test
    void is_shared() {
        assertTrue(RoleName.DEVELOPER.isShared());
        assertTrue(RoleName.MAINTAINER.isShared());
        assertTrue(RoleName.READ_ONLY.isShared());
        assertTrue(RoleName.REPORT_READ_ONLY.isShared());
        assertTrue(RoleName.ROLE_SYSTEM.isShared());
    }

    @Test
    void is_has_dev() {
        assertTrue(RoleName.DEVELOPER.isHasDev());
        assertFalse(RoleName.MAINTAINER.isHasDev());
        assertFalse(RoleName.READ_ONLY.isHasDev());
        assertFalse(RoleName.REPORT_READ_ONLY.isHasDev());
        assertTrue(RoleName.ROLE_SYSTEM.isHasDev());
    }

    @Test
    void is_read_in_prod() {
        assertTrue(RoleName.DEVELOPER.isReadInProd());
        assertTrue(RoleName.MAINTAINER.isReadInProd());
        assertTrue(RoleName.READ_ONLY.isReadInProd());
        assertTrue(RoleName.REPORT_READ_ONLY.isReadInProd());
        assertTrue(RoleName.ROLE_SYSTEM.isReadInProd());
    }

    @Test
    void is_read_report() {
        assertTrue(RoleName.DEVELOPER.isReadReport());
        assertTrue(RoleName.MAINTAINER.isReadReport());
        assertTrue(RoleName.READ_ONLY.isReadReport());
        assertTrue(RoleName.REPORT_READ_ONLY.isReadReport());
        assertTrue(RoleName.ROLE_SYSTEM.isReadReport());
    }

    @Test
    void is_execute_in_prod() {
        assertFalse(RoleName.DEVELOPER.isExecuteInProd());
        assertTrue(RoleName.MAINTAINER.isExecuteInProd());
        assertFalse(RoleName.READ_ONLY.isExecuteInProd());
        assertFalse(RoleName.REPORT_READ_ONLY.isExecuteInProd());
        assertTrue(RoleName.ROLE_SYSTEM.isExecuteInProd());
    }
}