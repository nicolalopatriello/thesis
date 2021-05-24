package it.nicolalopatriello.thesis.core.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.nicolalopatriello.thesis.common.dto.Dependency;
import it.nicolalopatriello.thesis.core.dto.DependencyVulnerability;
import it.nicolalopatriello.thesis.core.dto.dependecy.PythonVulnerability;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Log4j
@Service
public class PythonVulnerabilitiesServiceImpl implements PythonVulnerabilitiesService {

    @Value("${app.vulnerabilities.python.dir}")
    private String pythonVulnerabilitiesDirectory;

    @Value("${app.vulnerabilities.python.jsonfile:/data/insecure_full.json}")
    private String jsonPath;

    @Override
    public List<DependencyVulnerability> find(List<Dependency> dependencies) {
        List<DependencyVulnerability> list = Lists.newLinkedList();
        File f = new File(pythonVulnerabilitiesDirectory, jsonPath);
        if (!f.exists()) {
            return list;
        }
        Optional<PythonVulnerability> vulnerabilitiesOpt = PythonVulnerability.from(f);
        if (!vulnerabilitiesOpt.isPresent()) {
            return list;
        }
        PythonVulnerability vulnerabilities = vulnerabilitiesOpt.get();
        for (Dependency dependency : dependencies) {
            Set<String> set = Sets.newHashSet();
            List<PythonVulnerability.Item> obj = vulnerabilities.get(dependency.getName());
            if (obj != null) {
                for (PythonVulnerability.Item item : obj) {
                    if (item.match(dependency.getVersion())) {
                        set.add(item.getCve());
                    }
                }
                if (!set.isEmpty()) {
                    list.add(new DependencyVulnerability(dependency.getName(), set));
                }
            }
        }
        return list;
    }
}

//    public static void main(String[] args) throws FileNotFoundException, UnsopportedProgrammingLanguageException {
//        CveFinderServiceImpl c = new CveFinderServiceImpl();
//        List<Dependency> dependencies = Lists.newLinkedList();
//        Dependency d = new Dependency();
//        d.setProgrammingLanguage(ProgrammingLanguage.PYTHON);
//        d.setName("aegea");
//        d.setVersion("0.0");
//        dependencies.add(d);
//        System.err.println(dependencies.toString());
//        Optional<PythonVulnerability> cve = c.findPythonVulnerabilities(dependencies);
//        PythonVulnerability obj = cve.get();
//        System.err.println(obj.get("timetagger").get(0).getAdvisory());
//
//
////        File f = new File( "/var/thesis/vulnerabilities/python/data/insecure_full.json");
////        Optional<PythonVulnerability> re = PythonVulnerability.from(f);
////
////        System.err.println(Jsonizable.toJson(re.get()));
//
//
//    }
//}
