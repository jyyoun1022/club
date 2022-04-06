package org.zerock.club.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.club.entity.Note;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note,Long> {

    @EntityGraph(attributePaths = "clubMember",type = EntityGraph.EntityGraphType.LOAD)
    @Query( "select n from Note n where n.num=:num")
    Optional<Note>getWithClubMember(@Param("num")Long num);

    @EntityGraph(attributePaths = "clubMember" ,type = EntityGraph.EntityGraphType.LOAD)
    @Query( "select n from Note n where n.clubMember.email =:email" )
    List<Note> getList(@Param("email") String email);

}
