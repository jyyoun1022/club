package org.zerock.club.service;

import org.zerock.club.dto.NoteDTO;
import org.zerock.club.entity.ClubMember;
import org.zerock.club.entity.Note;

import java.util.List;

public interface NoteService {

    Long register(NoteDTO noteDTO);

    NoteDTO get(Long num);

    void modify(NoteDTO noteDTO);

    void remove(Long num);

    List<NoteDTO> getAllWithClubMember(String clubMemberEmail);

    default Note dtoToEntity(NoteDTO noteDTO){
        Note note = Note.builder()
                .num(noteDTO.getNum())
                .title(noteDTO.getTitle())
                .content(noteDTO.getContent())
                .clubMember(ClubMember.builder().email(noteDTO.getClubMemberEmail()).build())
                .build();

        return note;
    }

    default NoteDTO entityToDto(Note note){
        NoteDTO noteDTO = NoteDTO.builder()
                .num(note.getNum())
                .title(note.getTitle())
                .content(note.getContent())
                .clubMemberEmail(note.getClubMember().getEmail())
                .regDate(note.getRegDate())
                .modDate(note.getModDate())
                .build();

        return noteDTO;
    }
}
