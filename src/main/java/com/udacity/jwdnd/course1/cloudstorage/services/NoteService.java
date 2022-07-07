package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;
    private UserService userService;

    public NoteService(NoteMapper noteMapper, UserService userService) {
        this.noteMapper = noteMapper;
        this.userService = userService;
    }

    public List<Note> getAllNotes(Authentication authentication){
        return noteMapper.getAllNotes(userService.getUserId(authentication));
    }

    public int addNote(Note note, Authentication authentication){
        return noteMapper.insertNote(new Note(null, note.getNoteTitle(), note.getNoteDescription(),
                userService.getUserId(authentication)));
    }

    public int updateNote(Note note, Authentication authentication){
        note.setUserId(userService.getUserId(authentication));
        return noteMapper.editNote(note);
    }

    public int deleteNote(Integer noteId, Authentication authentication){
        return noteMapper.deleteNote(noteId, userService.getUserId(authentication));
    }
}
