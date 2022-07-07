package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/home")
public class HomeController {

    private FileService fileService;
    private NoteService noteService;
    private CredentialService credentialService;

    public HomeController(FileService fileService, NoteService noteService, CredentialService credentialService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String getHomePage(@ModelAttribute("note") Note note, @ModelAttribute("credential") Credential credential, Authentication authentication, Model model){
        model.addAttribute("fileNames", fileService.getAllFileNames(authentication));
        model.addAttribute("notes", noteService.getAllNotes(authentication));
        model.addAttribute("credentials", credentialService.getAllCredentials(authentication));
        return "home";
    }

    @PostMapping("/file/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication,
                             RedirectAttributes redirectAttributes, Model model) {
        String uploadError = null;
        if(fileService.getAllFileNames(authentication).contains(fileUpload.getOriginalFilename())){
            uploadError = "A file with the same name has already been saved, Please use a different name";
        }

        if(uploadError == null) {
            try {
                fileService.addFile(authentication, fileUpload);
            } catch (Exception e) {
                uploadError = "Error while uploading your file. Please try again";
            }
        }

        if (uploadError == null) {
            redirectAttributes.addFlashAttribute("fileUploadSuccess", true);
            redirectAttributes.addFlashAttribute("fileUploadSuccessMessage", "File Upload Successful!");
        } else {
            redirectAttributes.addFlashAttribute("fileUploadFailure", true);
            redirectAttributes.addFlashAttribute("fileUploadErrorMessage", uploadError);
        }

        return "redirect:/home";
    }

    @GetMapping("/file/download/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName, Authentication authentication) {
        File file = fileService.getFile(fileName, authentication);

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(file.getContentType()))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename" + file.getFileName())
            .body(file.getFileData());
    }

    @GetMapping("/file/delete/{fileName}")
    public String deleteFile(@PathVariable String fileName, Authentication authentication,  RedirectAttributes redirectAttributes) {
        String deleteError = null;

        int rowsDeleted = fileService.deleteFile(authentication, fileName);
        if (rowsDeleted < 1) {
            deleteError = "Error encountered while deleting file. Please try again.";
        }

        if (deleteError == null) {
            redirectAttributes.addFlashAttribute("fileDeleteSuccess", true);
            redirectAttributes.addFlashAttribute("fileDeleteSuccessMessage", "File Delete Successful!");
        } else {
            redirectAttributes.addFlashAttribute("fileDeleteFailure", true);
            redirectAttributes.addFlashAttribute("fileDeleteErrorMessage", deleteError);
        }

        return "redirect:/home";
    }

    @PostMapping("/note/create-or-update")
    public String createOrUpdateNote(@ModelAttribute("note") Note note, Authentication authentication,
                             RedirectAttributes redirectAttributes, Model model) {
        String error = null;
        if(note.getNoteId() == null) {
            int rowsAdded = noteService.addNote(note, authentication);

            if (rowsAdded < 1) {
                error = "Error encountered while creating note. Please try again";
            }

            if (error == null) {
                redirectAttributes.addFlashAttribute("noteCreationSuccess", true);
                redirectAttributes.addFlashAttribute("noteCreationSuccessMessage", "Note created successfully!");
            } else {
                redirectAttributes.addFlashAttribute("noteCreationFailure", true);
                redirectAttributes.addFlashAttribute("noteCreationSuccessMessage", error);
            }
        }else{
            int rowsUpdated = noteService.updateNote(note, authentication);

            if (rowsUpdated < 1) {
                error = "Error encountered while updating note. Please try again";
            }

            if (error == null) {
                redirectAttributes.addFlashAttribute("noteCreationSuccess", true);
                redirectAttributes.addFlashAttribute("noteCreationSuccessMessage", "Note updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("noteCreationFailure", true);
                redirectAttributes.addFlashAttribute("noteCreationSuccessMessage", error);
            }
        }

        return "redirect:/home";
    }

    @GetMapping("/note/delete/{noteId}")
    public String deleteNote(@PathVariable Integer noteId, Authentication authentication,  RedirectAttributes redirectAttributes) {
        String deleteError = null;

        int rowsDeleted = noteService.deleteNote(noteId, authentication);
        if (rowsDeleted < 1) {
            deleteError = "Error encountered while deleting note. Please try again.";
        }

        if (deleteError == null) {
            redirectAttributes.addFlashAttribute("noteDeleteSuccess", true);
            redirectAttributes.addFlashAttribute("noteDeleteSuccessMessage", "Note deleted successfully");
        } else {
            redirectAttributes.addFlashAttribute("noteDeleteFailure", true);
            redirectAttributes.addFlashAttribute("noteDeleteErrorMessage", deleteError);
        }

        return "redirect:/home";
    }

    @PostMapping("/credential/create-or-update")
    public String createOrUpdateCredential(@ModelAttribute("credential") Credential credential, Authentication authentication,
                                           RedirectAttributes redirectAttributes, Model model) {
        String error = null;
        if(credential.getCredentialId() == null) {
            int rowsAdded = credentialService.addCredential(credential, authentication);

            if (rowsAdded < 1) {
                error = "Error encountered while creating credential. Please try again";
            }

            if (error == null) {
                redirectAttributes.addFlashAttribute("credentialCreationSuccess", true);
                redirectAttributes.addFlashAttribute("credentialCreationSuccessMessage", "Credential created successfully!");
            } else {
                redirectAttributes.addFlashAttribute("credentialCreationFailure", true);
                redirectAttributes.addFlashAttribute("credentialCreationSuccessMessage", error);
            }
        }else{
            int rowsUpdated = credentialService.updateCredential(credential, authentication);

            if (rowsUpdated < 1) {
                error = "Error encountered while updating credential. Please try again";
            }

            if (error == null) {
                redirectAttributes.addFlashAttribute("credentialCreationSuccess", true);
                redirectAttributes.addFlashAttribute("credentialCreationSuccessMessage", "Credential updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("credentialCreationFailure", true);
                redirectAttributes.addFlashAttribute("credentialCreationSuccessMessage", error);
            }
        }

        return "redirect:/home";
    }

    @GetMapping("/credential/delete/{credentialId}")
    public String deleteCredential(@PathVariable Integer credentialId, Authentication authentication,  RedirectAttributes redirectAttributes) {
        String deleteError = null;

        int rowsDeleted = credentialService.deleteCredential(credentialId, authentication);
        if (rowsDeleted < 1) {
            deleteError = "Error encountered while deleting credential. Please try again.";
        }

        if (deleteError == null) {
            redirectAttributes.addFlashAttribute("credentialDeleteSuccess", true);
            redirectAttributes.addFlashAttribute("credentialDeleteSuccessMessage", "Credential deleted successfully");
        } else {
            redirectAttributes.addFlashAttribute("credentialDeleteFailure", true);
            redirectAttributes.addFlashAttribute("credentialDeleteErrorMessage", deleteError);
        }

        return "redirect:/home";
    }
}
