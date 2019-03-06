package ir.piana.dev.wrench.financial.participant;

import ir.piana.dev.wrench.core.QPException;
import ir.piana.dev.wrench.core.module.ParticipantActor;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Mohammad Rahmati, 3/5/2019
 */
public class ParticipantActorRepository {
    private static ParticipantActorRepository actorRepository;
    private Map<String, ParticipantActor> actorMap;

    private ParticipantActorRepository() {
        actorMap = new LinkedHashMap<>();
    }

    public static ParticipantActorRepository getSingleton() {
        if(actorRepository == null) {
            synchronized (ParticipantActorRepository.class) {
                actorRepository = new ParticipantActorRepository();
            }
        }
        return actorRepository;
    }

    public ParticipantActor getParticipantActor(String qualifiedName)
            throws QPException {
        ParticipantActor participantActor = actorMap.get(qualifiedName);
        if(participantActor != null)
            return participantActor;
        try {
            participantActor = (ParticipantActor) Class
                    .forName(qualifiedName).newInstance();
            actorMap.put(qualifiedName, participantActor);
        } catch (Exception e) {
            e.printStackTrace();
            throw new QPException(e);
        }
        return participantActor;
    }
}
