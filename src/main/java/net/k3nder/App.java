package net.k3nder;

import net.kender.core.sample.MC;
import net.kender.core.sample.VMC;
import net.kender.core.sample.custom.fabric.Fabric;
import net.kender.core.sample.custom.quilt.Quilt;
import net.kender.core.sample.Manifest;
import net.kender.kutils.OperativeSystem;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import static net.kender.kutils.OperativeSystem.*;

import java.net.URISyntaxException;
import java.nio.file.Path;

/**
 * Hello world!
 *
 */
@Command(name = "mci", mixinStandardHelpOptions = true, version = "1.0", description = "Descripción de tu programa", subcommands = {App.vanillaClass.class,App.fabricClass.class,App.quitlClass.class})
public class App {
	private static final OperativeSystem SYSTEM = thisOperativeSystem();
	private static final String USER = System.getProperty("user.name");
	private static final String DEFAULT_WINDOWS_PATH = "C:\\Users\\" + USER + "\\AppData\\Roaming\\.minecraft";
	private static final String DEFAUTL_LINUX_PATH = "/home/" + USER + "/.minecraft";
	private static final String DEFAULT_USERNAME = "default";


	@Command(name = "vanilla", description = "minecrat vanilla")
	public void vanilla(@Option(names = {"--version","-v"},required = true, description = "version") String version,
			@Option(names = { "--username","-n" }, required = false, description = "user name") String username,
			@Option(names = { "--destination",
					"-d" }, required = false, description = "destination of downloads") String destination,
			@Option(names = {"--quikplayw", "-qpw"}, required = false, description = "launch word") String wordPath,
			@Option(names = {"-Xmx"}, required = false,description = "max ram") int Xmx,
			@Option(names = {"-Xmx"}, required =false,description = "min ram") int Xms
			) {
		if (destination == null || destination.isEmpty()) destination = (SYSTEM == Linux ? DEFAUTL_LINUX_PATH : DEFAULT_WINDOWS_PATH);
		if (username == null || username.isEmpty()) username = DEFAULT_USERNAME;
		if (Xms == 0) Xms = 1;
		if (Xmx == 0) Xmx = 2;
		MC mc = new MC(Path.of(destination), new Manifest().getVersion(version));
		mc.setUser(username);
		mc.setRam(Xmx, Xms);
	
		mc.Run();
	}

	@Command(name = "quilt", description = "minecraft quilt loader")
	public void quilt(@Option(names = {"-v","--version"},required = true, description = "version mc") String version,
			@Option(names = {"-vl","--versionloader"},required = true, description = "version loader") String loader,
			@Option(names = { "--username","-n" }, required = false, description = "user name") String username,
			@Option(names = { "--destination","-d" }, required = false, description = "destination of downloads") String destination,
			@Option(names = {"-Xmx"}, required = false,description = "max ram") int Xmx,
			@Option(names = {"-Xmx"}, required =false,description = "min ram") int Xms)
			throws URISyntaxException {
		if (destination == null || destination.isEmpty()) destination = (SYSTEM == Linux ? DEFAUTL_LINUX_PATH : DEFAULT_WINDOWS_PATH);
		if (username == null || username.isEmpty()) username = DEFAULT_USERNAME;
		if (Xms == 0) Xms = 1;
		if (Xmx == 0) Xmx = 2;
		
		VMC version_VMC = new Quilt(version, loader);
		MC mc = new MC(Path.of(destination), version_VMC);
		mc.setUser(username);
		mc.setRam(Xmx, Xms);
		mc.Run();
	}

	@Command(name = "fabric", description = "minecraft quilt loader")
	public void fabric(@Option(names = {"-v","--version"},required = true, description = "version mc") String version,
			@Option(names = {"-vl","--versionloader"},required = true, description = "version loader") String loader,
			@Option(names = { "--username","-n" }, required = false, description = "user name") String username,
			@Option(names = { "--destination",
					"-d" }, required = false, description = "destination of downloads") String destination,
			@Option(names = {"-Xmx"}, required = false,description = "max ram") int Xmx,
			@Option(names = {"-Xmx"}, required =false,description = "min ram") int Xms)
			throws URISyntaxException {
		if (destination == null || destination.isEmpty()) destination = (SYSTEM == Linux ? DEFAUTL_LINUX_PATH : DEFAULT_WINDOWS_PATH);
		if (username == null || username.isEmpty()) username = DEFAULT_USERNAME;
		if (Xms == 0) Xms = 1;
		if (Xmx == 0) Xmx = 2;
		VMC version_VMC = new Fabric(version, loader);
		MC mc = new MC(Path.of(destination), version_VMC);
		mc.setUser(username);
		mc.setRam(Xmx, Xms);
		mc.Run();
	}
	
	@Command(name = "vanilla-conf",description = "vanilla conf")
	static class vanillaClass{
		 @Command(name = "versions", description = "minecraft versions")
		public void versions() {
			for(VMC version : new Manifest().getList()) {
				System.out.println("version: " + version.getID());
			}
		}
	}
	@Command(name = "quilt-conf",description = "vanilla conf")
	static class quitlClass{
		 @Command(name = "versions", description = "minecraft versions loader")
		public void versions(@Option(names = {"-v","--version"},required = true,description = "verison of mc filter") String version) throws URISyntaxException {
			for(String versions : Quilt.getLoadersVersions(version)) {
				System.out.println("version: " + versions);
			}
		}
	}
	@Command(name = "fabric-conf",description = "vanilla conf")
	static class fabricClass{
		 @Command(name = "versions", description = "minecraft versions loader")
		public void versions(@Option(names = {"-v","--version"},required = true,description = "verison of mc filter") String version) throws URISyntaxException {
			for(String versions : Fabric.getLoadersVersions(version)) {
				System.out.println("version: " + versions);
			}
		}
	}

	public static void main(String[] args) {
		new CommandLine(new App()).execute(args);
	}
}
