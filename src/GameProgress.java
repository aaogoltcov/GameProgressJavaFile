import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class GameProgress implements Serializable {
  private static final long serialVersionUID = 1L;
  private final int health;
  private final int weapons;
  private final int lvl;
  private final double distance;

  public GameProgress(int health, int weapons, int lvl, double distance) {
    this.health = health;
    this.weapons = weapons;
    this.lvl = lvl;
    this.distance = distance;
  }

  public static void zipFiles(String[] fileUrls, String zipUrl) throws Exception {
    try (
        ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipUrl))
    ) {
      for (int index = 0; index < fileUrls.length; index++) {
        FileInputStream fis = new FileInputStream(fileUrls[index]);
        ZipEntry entry = new ZipEntry("save" + index + ".dat");
        zout.putNextEntry(entry);

        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        zout.write(buffer);
        zout.closeEntry();

        File file = new File(fileUrls[index]);
        file.delete();
      }
    } catch (Exception error) {
      throw new Exception("При архивации файлов произошла ошибка: " + error);
    }
  }

  public static void openZip(String zipFileUrl, String unZipDirectory) throws Exception {
    try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFileUrl))) {
      ZipEntry zipEntry;
      String name;

      while ((zipEntry = zin.getNextEntry()) != null) {
        name = zipEntry.getName();

        FileOutputStream fout = new FileOutputStream(unZipDirectory + "/" + name);

        for (int c = zin.read(); c != -1; c = zin.read()) {
          fout.write(c);
        }

        fout.flush();
        zin.closeEntry();
        fout.close();
      }

    } catch (Exception error) {
      throw new Exception("При распаковке файла произошла ошибка: " + error);
    }
  }

  public static void openProgress(String fileUrl) throws Exception {
    try (FileInputStream fis = new FileInputStream(fileUrl);
         ObjectInputStream ois = new ObjectInputStream(fis)) {
      GameProgress gameProgress = (GameProgress) ois.readObject();

      System.out.println(gameProgress);
    } catch (Exception error) {
      throw new Exception("При десериализации файла произошла ошибка: " + error);
    }
  }

  @Override
  public String toString() {
    return "GameProgress{" +
        "health=" + health +
        ", weapons=" + weapons +
        ", lvl=" + lvl +
        ", distance=" + distance +
        '}';
  }

  void saveGame(String saveUrl) throws Exception {
    try (FileOutputStream fos = new FileOutputStream(saveUrl)) {
      ObjectOutputStream oos = new ObjectOutputStream(fos);

      oos.writeObject(this);
    } catch (NotSerializableException error) {
      throw new Exception("При сохранении файла произошла ошибка: " + error);
    }
  }
}
