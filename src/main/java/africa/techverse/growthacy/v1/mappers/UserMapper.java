package africa.techverse.growthacy.v1.mappers;

import africa.techverse.growthacy.v1.dtos.UserDto;
import africa.techverse.growthacy.v1.entities.User;
import africa.techverse.growthacy.v1.enums.UserStatus;
import africa.techverse.growthacy.v1.enums.UserType;
import org.springframework.beans.BeanUtils;

public class UserMapper {

    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        if (user.getType() == UserType.AMBASSADOR.getValue()) userDto.setType("ambassador");
        else if (user.getType() == UserType.COMPANY.getValue()) userDto.setType("company");
        else if (user.getType() == UserType.ADMIN.getValue()) userDto.setType("admin");

        if (user.getStatus() == UserStatus.ACTIVE.getValue()) userDto.setStatus("active");
        else if (user.getStatus() == UserStatus.SUSPENDED.getValue()) userDto.setStatus("suspended");
        else if (user.getStatus() == UserStatus.BANNED.getValue()) userDto.setStatus("banned");

        userDto.setEmailVerified(user.getEmailVerifiedAt() != null);
        userDto.setPhoneVerified(user.getPhoneVerifiedAt() != null);
        return userDto;
    }
}
