package joljak.homecoming.service;

import joljak.homecoming.entity.Comment;
import joljak.homecoming.entity.Scomment;
import joljak.homecoming.repository.BoardRepository;
import joljak.homecoming.repository.CommentRepository;
import joljak.homecoming.repository.ScommentRepository;
import joljak.homecoming.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScommentService {
    @Autowired
    private ScommentRepository scommentRepository;

    public Scomment saveScomment(Scomment scomment) {
        return scommentRepository.save(scomment);
    }

    public List<Scomment> getScommentsByBoardId(Long sightingboardId) {
        return scommentRepository.findBySightingBoardId(sightingboardId);
    }

    public Scomment updateScomment(Long id, String text) {
        Scomment scomment = scommentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scomment not found"));
        scomment.setContent(text);
        return scommentRepository.save(scomment);
    }

    public void deleteScomment(Long id) {
        scommentRepository.deleteById(id);
    }
}
