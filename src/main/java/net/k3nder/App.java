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
import picocli.CommandLine.Parameters;
import static net.kender.kutils.OperativeSystem.*;

import java.net.URISyntaxException;
import java.nio.file.Path;

/**
 * Hello world!
 *
 */
@Command(name = "mci", mixinStandardHelpOptions = true, version = "1.0", description = "Descripción de tu programa", subcommands = {})
public class App {
	private static final OperativeSystem SYSTEM = thisOperativeSystem();
	private static final String USER = System.getProperty("user.name");

	@Command(name = "vanilla", description = "minecrat vanilla")
	public void vanilla(@Option(names = {"--version","-v"},required = true, description = "version") String version,
			@Option(names = { "--username","-n" }, required = false, description = "user name") String username,
			@Option(names = { "--destination",
					"-d" }, required = false, description = "destination of downloads") String destination,
			@Option(names = {"--quikplayw", "-qpw"}, required = false, description = "launch word") String wordPath
			) {
		if (destination == null || destination == "") {
			destination = (SYSTEM == Linux ? "/home/" + USER + "/.minecraft"
					: "C:\\Users\\" + USER + "\\AppData\\Roaming\\.minecraft");
		}
		if (username == null || username == "") {
			username = "default";
		}
		MC mc = new MC(Path.of(destination), new Manifest().getVersion(version));
		mc.setUser(username);
		mc.Run();
	}

	@Command(name = "quilt", description = "minecraft quilt loader")
	public void quilt(@Option(names = {"-v","--version"},required = true, description = "version mc") String version,
			@Option(names = {"-vl","--versionloader"},required = true, description = "version loader") String loader,
			@Option(names = { "--username","-n" }, required = false, description = "user name") String username,
			@Option(names = { "--destination","-d" }, required = false, description = "destination of downloads") String destination)
			throws URISyntaxException {
		if (destination == null || destination == "") {
			destination = (SYSTEM == Linux ? "/home/" + USER + "/.minecraft"
					: "C:\\Users\\" + USER + "\\AppData\\Roaming\\.minecraft");
		}
		if (username == null || username == "") {
			username = "default";
		}
		VMC version_VMC = new Quilt(version, loader);
		MC mc = new MC(Path.of(destination), version_VMC);
		mc.setUser(username);
		mc.Run();
	}

	@Command(name = "fabric", description = "minecraft quilt loader")
	public void fabric(@Option(names = {"-v","--version"},required = true, description = "version mc") String version,
			@Option(names = {"-vl","--versionloader"},required = true, description = "version loader") String loader,
			@Option(names = { "--username","-n" }, required = false, description = "user name") String username,
			@Option(names = { "--destination",
					"-d" }, required = false, description = "destination of downloads") String destination)
			throws URISyntaxException {
		if (destination == null || destination == "") {
			destination = (SYSTEM == Linux ? "/home/" + USER + "/.minecraft"
					: "C:\\Users\\" + USER + "\\AppData\\Roaming\\.minecraft");
		}
		if (username == null || username == "") {
			username = "default";
		}
		VMC version_VMC = new Fabric(version, loader);
		MC mc = new MC(Path.of(destination), version_VMC);
		mc.setUser(username);
		mc.Run();
	}

	public static void main(String[] args) {
		new CommandLine(new App()).execute(args);
	}
}
