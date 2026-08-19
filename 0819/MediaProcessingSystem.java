abstract class MediaFile {
    private String title;
    private int sizeInMb;

    MediaFile(String title, int sizeInMb) {
        this.title = title;
        this.sizeInMb = Math.max(0, sizeInMb);
    }

    String getTitle() {
        return title;
    }

    int getSizeInMb() {
        return sizeInMb;
    }

    abstract void showInfo();
}

interface Playable {
    void play();
}

interface Compressible {
    void compress();
}

class ImageFile extends MediaFile implements Compressible {
    ImageFile(String title, int sizeInMb) {
        super(title, sizeInMb);
    }

    @Override
    void showInfo() {
        System.out.println("Image File: " + getTitle() + " (" + getSizeInMb() + "MB)");
    }

    @Override
    public void compress() {
        System.out.println("-> 壓縮圖片：「" + getTitle() + "」完成，畫質優化減小體積。");
    }
}

class AudioFile extends MediaFile implements Playable, Compressible {
    AudioFile(String title, int sizeInMb) {
        super(title, sizeInMb);
    }

    @Override
    void showInfo() {
        System.out.println("Audio File: " + getTitle() + " (" + getSizeInMb() + "MB)");
    }

    @Override
    public void play() {
        System.out.println("-> 播放音訊：「" + getTitle() + "」正在撥放音樂...");
    }

    @Override
    public void compress() {
        System.out.println("-> 壓縮音訊：「" + getTitle() + "」完成，轉換為高效率位元率。");
    }
}

class VideoFile extends MediaFile implements Playable, Compressible {
    VideoFile(String title, int sizeInMb) {
        super(title, sizeInMb);
    }

    @Override
    void showInfo() {
        System.out.println("Video File: " + getTitle() + " (" + getSizeInMb() + "MB)");
    }

    @Override
    public void play() {
        System.out.println("-> 播放影片：「" + getTitle() + "」正在撥放畫面與聲音...");
    }

    @Override
    public void compress() {
        System.out.println("-> 壓縮影片：「" + getTitle() + "」完成，調整解析度與編碼。");
    }
}

public class MediaProcessingSystem {
    public static void main(String[] args) {
        MediaFile[] mediaFiles = {
                new ImageFile("Vacation.jpg", 5),
                new AudioFile("Song.mp3", 45),
                new VideoFile("Tutorial.mp4", 850)
        };

        for (MediaFile media : mediaFiles) {
            media.showInfo();

            if (media instanceof Playable playable) {
                playable.play();
            }

            if (media instanceof Compressible compressible) {
                compressible.compress();
            }

            System.out.println("--------------------");
        }
    }
}