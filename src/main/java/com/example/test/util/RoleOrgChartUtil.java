package com.example.test.util;

import com.example.test.dto.OrgChartDTO;
import com.example.test.entity.Role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoleOrgChartUtil {

    private List<Role> roleList;

    private Map<Integer, Role> seqRoleMap;

    private Map<Long, Integer> roleIdSeqMap;

    private ArrayList<Integer>[] topBottom;

    private int superSeq = 0;

    public RoleOrgChartUtil(List<Role> roleList) {
        this.roleList     = roleList;
        this.seqRoleMap   = new HashMap<>();
        this.roleIdSeqMap = new HashMap<>();
        this.topBottom    = new ArrayList[roleList.size()];

        for(int i = 0; i< topBottom.length; i++) topBottom[i] = new ArrayList<>();

        initialize();
    }

    /**
     * N : 권한의 수
     * O(N) = 2N
     */
    private void initialize() {
        int seq = 0;

        for (Role role : roleList) {
            seqRoleMap.put(seq, role);
            roleIdSeqMap.put(role.getId(), seq);

            seq ++;
        }// for

        for (Role role : roleList) {
            if(role.getParentRole() == null) {
                superSeq = roleIdSeqMap.get(role.getId());

                continue;
            }// if

            Role parentRole = role.getParentRole();

            Integer parentSeq = roleIdSeqMap.get(parentRole.getId());
            Integer childSeq  = roleIdSeqMap.get(role.getId());

            topBottom[parentSeq.intValue()].add(childSeq);
        }// for
    }// initialize

    public OrgChartDTO createOrgChartDTO() {
        Role superRole = seqRoleMap.get(superSeq);

        OrgChartDTO superOrgChartDTO = orgChartDTOBuild(String.valueOf(superRole.getId()), superRole.getRoleType());

        dfs(superSeq, superOrgChartDTO);

        return superOrgChartDTO;
    }// createOrgChartDTO

    /**
     * 트리 탐색.
     * N : 권한의 갯수
     * O(N) = N + (N - 1)
     * @param currentSeq
     * @param currentChartDTO
     */
    private void dfs(int currentSeq, OrgChartDTO currentChartDTO) {

        for (Integer next : topBottom[currentSeq]) {
            Role childRole = seqRoleMap.get(next);
            OrgChartDTO childChartDTO = orgChartDTOBuild(String.valueOf(childRole.getId()), childRole.getRoleType());

            dfs(next, childChartDTO);

            currentChartDTO.getChildren().add(childChartDTO);
        }// for

    }// dfs

    private OrgChartDTO orgChartDTOBuild(String title, String name) {
        return OrgChartDTO.builder()
                .title(title)
                .name(name)
                .children(new ArrayList<>())
                .build();
    }// orgChartDTOBuild

}// RoleUtil























