package miage.nanterre.m1app.realtimekeynote.Service;

import miage.nanterre.m1app.realtimekeynote.Model.VideoProcessState;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceAnalyticsRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.VideoProcessStateRepository;
import miage.nanterre.m1app.realtimekeynote.VideoProcessing.AnalysisThread;

public class VideoProcessService {
     private SeanceRepository seanceRepository;
     private SeanceAnalyticsRepository seanceAnalyticsRepository;
     private VideoProcessStateRepository videoProcessStateRepository;

    public VideoProcessService( SeanceRepository seanceRepository,SeanceAnalyticsRepository seanceAnalyticsRepository, VideoProcessStateRepository videoProcessStateRepository) {
        this.seanceRepository = seanceRepository;
        this.seanceAnalyticsRepository = seanceAnalyticsRepository;
        this.videoProcessStateRepository = videoProcessStateRepository;
    }

    public void analyse(String nom,long seanceId) {
        AnalysisThread videoProcess = new AnalysisThread();
        videoProcess.setVideoName("WIN_20181223_15_29_59_Pro.mp4");
        // VideoProcessState videoProcessState = new VideoProcessState(seanceRepository.findById(seanceId).get());
       // videoProcessStateRepository.save(videoProcessState);
        videoProcess.setSeanceAnalyticsRepository(seanceAnalyticsRepository);
        videoProcess.setSeance(seanceRepository.findById(seanceId).get());
        videoProcess.setVideoProcessStateRepository(videoProcessStateRepository);
        videoProcess.start();
    }


}
