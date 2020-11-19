package org.yeepay.agent.secruity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.yeepay.core.common.constant.MchConstant;
import org.yeepay.core.entity.AgentInfo;
import org.yeepay.core.entity.MchInfo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(String userName, AgentInfo agentInfo) {
        return new JwtUser(
                agentInfo.getAgentId(),
                agentInfo.getAgentName(),
                agentInfo.getStatus(),
                userName,
                agentInfo.getPassword(),
                agentInfo.getEmail(),
                mapToGrantedAuthorities(MchConstant.AGENT_ROLE_NORMAL),
                agentInfo.getLastPasswordResetTime()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(String role) {
        String[] roles = role.split(":");
        List<String> authorities = Arrays.asList(roles);
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}