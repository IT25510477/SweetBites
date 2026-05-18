package com.bakery.service;

import com.bakery.model.ContactMessage;
import com.bakery.repository.ContactMessageRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactMessageService extends AbstractCrudService<ContactMessage, Long> {

    private final ContactMessageRepository messageRepository;

    public ContactMessageService(ContactMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    protected JpaRepository<ContactMessage, Long> getRepository() {
        return messageRepository;
    }

    public List<ContactMessage> findAllSorted() {
        return messageRepository.findAllByOrderByCreatedAtDesc();
    }
}
