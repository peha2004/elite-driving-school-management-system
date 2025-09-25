    package org.example.elite_driving_school_management_system.bo.custom.impl;

    import org.example.elite_driving_school_management_system.bo.custom.LessonBO;
    import org.example.elite_driving_school_management_system.bo.exception.DuplicateException;
    import org.example.elite_driving_school_management_system.bo.exception.InUseException;
    import org.example.elite_driving_school_management_system.bo.exception.NotFoundException;
    import org.example.elite_driving_school_management_system.dao.DAOFactory;
    import org.example.elite_driving_school_management_system.dao.custom.CourseDAO;
    import org.example.elite_driving_school_management_system.dao.custom.InstructorDAO;
    import org.example.elite_driving_school_management_system.dao.custom.LessonDAO;
    import org.example.elite_driving_school_management_system.dao.custom.StudentDAO;
    import org.example.elite_driving_school_management_system.dto.LessonDTO;
    import org.example.elite_driving_school_management_system.entity.Course;
    import org.example.elite_driving_school_management_system.entity.Instructor;
    import org.example.elite_driving_school_management_system.entity.Lesson;
    import org.example.elite_driving_school_management_system.entity.Student;

    import java.sql.Timestamp;
    import java.time.LocalDateTime;
    import java.time.LocalTime;
    import java.util.ArrayList;
    import java.util.List;

    public class LessonBOImpl implements LessonBO {
        private final LessonDAO lessonDAO = (LessonDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.LESSON);
        private final StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STUDENT);
        private final CourseDAO courseDAO = (CourseDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.COURSE);
        private final InstructorDAO instructorDAO = (InstructorDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.INSTRUCTOR);
        @Override
        public boolean saveLesson(LessonDTO dto) throws Exception {
            Lesson existing = lessonDAO.search(dto.getLessonId());
            if (existing != null) {
                throw new DuplicateException("Lesson with ID " + dto.getLessonId() + " already exists!");
            }
            Lesson lesson = new Lesson();
            lesson.setLessonId(dto.getLessonId());
            lesson.setStudent(new Student(dto.getStudentId()));
            lesson.setCourse(new Course(dto.getCourseId()));
            lesson.setInstructor(new Instructor(dto.getInstructorId()));
            lesson.setLessonDate(Timestamp.valueOf(LocalDateTime.of(dto.getLessonDate(), LocalTime.parse(dto.getLessonTime()))));
            lesson.setDuration(dto.getDuration());

            return lessonDAO.save(lesson);
        }

        @Override
        public boolean updateLesson(LessonDTO dto) throws Exception {
            Lesson existing = lessonDAO.search(dto.getLessonId());
            if (existing == null) {
                throw new NotFoundException("Lesson with ID " + dto.getLessonId() + " not found!");
            }

            Lesson lesson = new Lesson();
            lesson.setLessonId(dto.getLessonId());
            lesson.setStudent(new Student(dto.getStudentId()));
            lesson.setCourse(new Course(dto.getCourseId()));
            lesson.setInstructor(new Instructor(dto.getInstructorId()));
            lesson.setLessonDate(Timestamp.valueOf(LocalDateTime.of(dto.getLessonDate(), LocalTime.parse(dto.getLessonTime()))));
            lesson.setDuration(dto.getDuration());

            return lessonDAO.update(lesson);
        }

        @Override
        public boolean deleteLesson(String id) throws Exception {
            Lesson existing = lessonDAO.search(id);
            if (existing == null) {
                throw new NotFoundException("Lesson with ID " + id + " not found!");
            }
            if (existing.getStudent() != null || existing.getCourse() != null || existing.getInstructor() != null) {
                throw new InUseException("Lesson with ID " + id + " is in use and cannot be deleted!");
            }
            return lessonDAO.delete(id);
        }


        @Override
        public List<LessonDTO> getAllLessons() throws Exception {
            List<Lesson> all = lessonDAO.getAll();
            List<LessonDTO> dtos = new ArrayList<>();
            for (Lesson lesson : all) {
                dtos.add(new LessonDTO(
                        lesson.getLessonId(),
                        lesson.getLessonDate().toLocalDateTime().toLocalDate(),
                        lesson.getLessonDate().toLocalDateTime().toLocalTime().toString(),
                        lesson.getDuration(),
                        lesson.getCourse().getCourseId(),
                        lesson.getStudent().getStudentID(),
                        lesson.getInstructor().getInstructorId()
                ));
            }
            return dtos;
        }

        @Override
        public String generateNextLessonId() throws Exception {
            String lastId = lessonDAO.getLastLessonId();
            if (lastId != null) {
                int id = Integer.parseInt(lastId.substring(1)) + 1;
                return String.format("L%03d", id);
            } else {
                return "L001";
            }

        }

        @Override
        public List<String> getAllStudentIds() throws Exception {
            List<Student> students = studentDAO.getAll();
            List<String> ids = new ArrayList<>();
            for (Student s : students) {
                ids.add(s.getStudentID());
            }
            return ids;
        }

        @Override
        public List<String> getAllCourseIds() throws Exception {
            List<Course> courses = courseDAO.getAll();
            List<String> ids = new ArrayList<>();
            for (Course c : courses) {
                ids.add(c.getCourseId());
            }
            return ids;
        }

        @Override
        public List<String> getAllInstructorIds() throws Exception {
            List<Instructor> instructors = instructorDAO.getAll();
            List<String> ids = new ArrayList<>();
            for (Instructor i : instructors) {
                ids.add(i.getInstructorId());
            }
            return ids;
        }
    }
