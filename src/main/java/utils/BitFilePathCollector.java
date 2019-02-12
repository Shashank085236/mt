package utils;

import constants.Constants;
import flush.FlushStore;
import flush.FlushVisitorImpl;
import flush.IFlushVisitor;
import flush.OnDemandInventoryFilesWriterImpl;
import flush.paths.BitFilePath;
import flush.paths.IPath;
import flush.paths.LevelPath;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.Objects;

public class BitFilePathCollector {

    private IPath rootPath;
    private IFlushVisitor flushVisitor;
    public static final Path EOF = FileSystems.getDefault().getPath("EOF");
    Constants constants = new Constants();

    public BitFilePathCollector(IPath rootPath) {
        Objects.requireNonNull(rootPath);
        this.rootPath = rootPath;
        String location = rootPath.getDepth() == 1 ? constants.inventoryStore() + rootPath.getPath().toAbsolutePath().toString().split(rootPath.getRT())[1].replaceAll(File.separator,"_") : constants.levelStore();
        FlushStore store = constants.sourceDBOrFile.equalsIgnoreCase("File") ? new OnDemandInventoryFilesWriterImpl(location,true) : null;
        this.flushVisitor = new FlushVisitorImpl(store);
    }

    public void collectFilesAndFlush() {
        try {
            Files.walkFileTree(rootPath.getPath(), EnumSet.noneOf(FileVisitOption.class), rootPath.getDepth(), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                    Objects.requireNonNull(path);
                    Objects.requireNonNull(attrs);
                    IPath p = IPath.TYPE.LEVEL.equals(rootPath.getType()) ? new LevelPath(path, rootPath.getRT()) : new BitFilePath(path, rootPath.getRT());
                    p.accept(flushVisitor);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // Send EOF signal to close resources.
            IPath p = IPath.TYPE.LEVEL.equals(rootPath.getType()) ? new LevelPath(EOF, rootPath.getRT()) : new BitFilePath(EOF, rootPath.getRT());
            p.accept(flushVisitor);
        }
    }

    public static void main(String[] args) {
        BitFilePathCollector collector = new BitFilePathCollector(new LevelPath(FileSystems.getDefault().getPath("C:\\Workspace\\learn\\RT_00006A7166B08490CF0"),""));
        long beg = System.currentTimeMillis();
        collector.collectFilesAndFlush();
        long end = System.currentTimeMillis();
        System.out.println("Time taken (ms) - "+ (end - beg));
    }

}
