package ir.piana.dev.wrench.rest.http.staticresource;

import org.jpos.util.Logger;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * @author Mohammad Rahmati, 4/19/2017 11:00 AM
 */
public class QPStaticResourceResolver implements Runnable {
    private Logger logger = Logger.getLogger(
            QPStaticResourceResolver.class.getSimpleName());
    private Map<String, QPStaticResource> assetsMap =
            new LinkedHashMap<>();
    private Path rootPath;
    private static ReentrantLock lock = new ReentrantLock();
    private static QPStaticResourceResolver resourceResolver = null;
    private WatchService watchService;
    private WatchKey watchKey;

    private static MimetypesFileTypeMap mimeTypesMap = null;

    static {
        mimeTypesMap = new MimetypesFileTypeMap(
                QPStaticResourceResolver.class.getResourceAsStream("/mime.types")
        );
    }

    private QPStaticResourceResolver(Path rootPath)
            throws IOException, InterruptedException {
        if(rootPath != null) {
            this.rootPath = rootPath;

            watchService = rootPath
                    .getFileSystem().newWatchService();
            registerRecursive(this.rootPath, watchService);

            QPStaticResourceResolver resolver = this;
            Executors.newSingleThreadExecutor()
                    .execute(resolver);
        }
    }

    public static QPStaticResourceResolver getInstance(
            String rootPath) {
        try {
            lock.tryLock(100, TimeUnit.MILLISECONDS);
            Path path = Paths.get(rootPath);
            if(Files.exists(path)) {
                return new QPStaticResourceResolver(path);
            } else {
                throw new Exception("this paths not exist");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public void run() {
        try {
            while ((watchKey = watchService.take()) != null) {
                List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
                for (WatchEvent we : watchEvents) {
                    WatchEvent.Kind changedKind = we.kind();
                    Path changedPath = (Path) we.context();
                    Path dir = (Path) this.watchKey.watchable();
                    Path fullPath = dir.resolve(changedPath);
                    File file = new File(fullPath.toString());
                    if (changedKind == ENTRY_DELETE) {
                        if (file.isDirectory()) {
                            for (String key : assetsMap.keySet()) {
                                if (key.startsWith(fullPath.toString())) {
                                    assetsMap.remove(key);
                                }
                            }
                        } else {
                            String substring = fullPath.toString().substring(
                                    fullPath.toString().indexOf(rootPath.toString()) + rootPath.toString().length() + 1)
                                    .replace("\\", "/");
//                    assetsMap.remove(fullPath);
                            assetsMap.remove(substring);
                        }
                    } else if (changedKind == ENTRY_MODIFY) {
                        if (!file.isDirectory()) {
                            String substring = fullPath.toString().substring(
                                    fullPath.toString().indexOf(rootPath.toString()) + rootPath.toString().length() + 1)
                                    .replace("\\", "/");
//                    assetsMap.remove(fullPath.toString());
                            assetsMap.remove(substring);
                        }
                    }
                }
                watchKey.reset();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void registerRecursive(
            final Path root,
            final WatchService watchService)
            throws IOException {
        // register all subfolders
        Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(
                    Path dir,
                    BasicFileAttributes attrs)
                    throws IOException {
                dir.register(watchService,
                        ENTRY_CREATE,
                        ENTRY_DELETE,
                        ENTRY_MODIFY);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private QPStaticResource reload(String path)
            throws Exception {
        QPStaticResource pianaAsset = null;
        File file = new File(rootPath.toString(), path);
        try (FileInputStream fileInputStream =
                     new FileInputStream(file)) {
            int available = fileInputStream.available();
            String mediaType = mimeTypesMap.getContentType(file);
            if(mediaType == null || mediaType.isEmpty()
                    || mediaType.equalsIgnoreCase("application/octet-stream")) {
                mediaType = Files.probeContentType(file.toPath());
                mediaType = mediaType == null || mediaType.isEmpty() ?
                        "application/octet-stream" : mediaType;
                if(mediaType.equalsIgnoreCase("application/octet-stream")){
                    mediaType = file.toPath().getFileName()
                            .toString().endsWith(".js") ?
                            "application/javascript" : mediaType;
                }
            }

            byte[] bytes = new byte[available];
            int read = fileInputStream.read(bytes, 0, available);
            if(read == available) {
                pianaAsset = new QPStaticResource(bytes,
                        rootPath.toString(),
                        path, mediaType);
//                assetsMap.put(pianaAsset.getPath().toString(),
//                        pianaAsset);
                assetsMap.put(path, pianaAsset);
//                assetsMap.put(file.getPath(), pianaAsset);
            }
//            logger.info("load asset");
        } catch (Exception ex) {
//            logger.error(ex.getMessage(), ex);
            throw new Exception("asset not found");
        }
        return pianaAsset;
    }

    public QPStaticResource resolve(String path)
            throws Exception {
        if(path == null)
            return null;
//        PianaAsset pianaAsset = assetsMap.get(rootPath.toString().concat("\\").concat(path));
        QPStaticResource pianaAsset = assetsMap.get(path);
        if(pianaAsset != null) {
            return pianaAsset;
        }
        pianaAsset = reload(path);
        return pianaAsset;
    }
}
